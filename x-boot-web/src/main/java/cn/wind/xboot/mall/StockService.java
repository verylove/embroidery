package cn.wind.xboot.mall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 14:21
 * @Description: redis库存
 */
@Service
public class StockService {
    Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private RedisLock redisLock;

    /**
     * 库存不足
     */
    public static final int LOW_STOCK = 0;

    /**
     * 库存未初始化
     */
    public static final long UNINITIALIZED_STOCK = -3L;

    /**
     * Redis 客户端
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 执行扣库存的脚本
     */
    public static final String STOCK_LUA;

    static {
        /**
         *
         * @desc 扣减库存Lua脚本
         * 库存（stock）-1：表示不限库存
         * 库存（stock）0：表示没有库存
         * 库存（stock）大于0：表示剩余库存
         *
         * @params 库存key
         * @return
         * 		-3:库存未初始化
         * 		-2:库存不足
         * 		-1:不限库存
         * 		大于等于0:剩余库存（扣减之后剩余的库存）
         * 	    redis缓存的库存(value)是-1表示不限库存，直接返回-1
         */
        StringBuilder sb = new StringBuilder();
        sb.append("if (redis.call('exists', KEYS[1]) == 1) then");
        sb.append("    local stock = tonumber(redis.call('get', KEYS[1]));");
        sb.append("    local num = tonumber(ARGV[1]);");
        sb.append("    if (stock == -1) then");
        sb.append("        return -1;");
        sb.append("    end;");
        sb.append("    if (stock >= num) then");
        sb.append("        return redis.call('incrby', KEYS[1], 0 - num);");
        sb.append("    end;");
        sb.append("    return -2;");
        sb.append("end;");
        sb.append("return -3;");
        STOCK_LUA = sb.toString();
    }

    /**
     * @param key           库存key
     * @param expire        库存有效时间,单位秒
     * @param num           扣减数量
     * @param stockCallback 初始化库存回调函数
     * @return -2:库存不足; -1:不限库存; 大于等于0:扣减库存之后的剩余库存
     */
    public long stock(String key, long expire, int num, IStockCallback stockCallback) {
        long stock = stock(key, num);
        // 初始化库存
        if (stock == UNINITIALIZED_STOCK) {
            try {
                // 获取锁
                if (redisLock.lock(key+"_lock",0L,60 * 1000L)) {
                    // 双重验证，避免并发时重复回源到数据库
                    stock = stock(key, num);
                    if (stock == UNINITIALIZED_STOCK) {
                        // 获取初始化库存
                        final long initStock = stockCallback.getStock();
                        // 将库存设置到redis
                        redisTemplate.opsForValue().set(key, initStock, expire, TimeUnit.DAYS);
                        // 调一次扣库存的操作
                        stock = stock(key, num);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                redisLock.unLock(key+"_lock");
            }

        }
        return stock;
    }

    /**
     * 加库存
     *
     * @param key    库存key
     * @param expire 过期时间（秒）
     * @param num    库存数量
     * @return
     */
    public long addStock(String key, Long expire, int num, IStockCallback stockCallback) {
        boolean hasKey = redisTemplate.hasKey(key);
        // 判断key是否存在，存在就直接更新
        if (hasKey) {
            return redisTemplate.opsForValue().increment(key, num);
        }

//        Assert.notNull(expire,"初始化库存失败，库存过期时间不能为null");
        try {
            if (redisLock.lock(key+"_lock",0L,60 * 1000L)) {
                // 获取到锁后再次判断一下是否有key
                hasKey = redisTemplate.hasKey(key);
                if (!hasKey) {
                    // 获取初始化库存
                    final long initStock = stockCallback.getStock();
                    // 初始化库存
                    if(expire != null){
                        redisTemplate.opsForValue().set(key, initStock, expire, TimeUnit.DAYS);
                    }else {
                        redisTemplate.opsForValue().set(key, initStock);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            redisLock.unLock(key+"_lock");
        }

        return num;
    }

    /**
     * 获取库存
     *
     * @param key 库存key
     * @return -1:不限库存; 大于等于0:剩余库存
     */
    public int getStock(String key) {
        Integer stock = (Integer) redisTemplate.opsForValue().get(key);
        return stock == null ? -1 : stock;
    }

    /**
     * 扣库存
     *
     * @param key 库存key
     * @param num 扣减库存数量
     * @return 扣减之后剩余的库存【-3:库存未初始化; -2:库存不足; -1:不限库存; 大于等于0:扣减库存之后的剩余库存】
     */
    private Long stock(String key, int num) {
        // 脚本里的KEYS参数
        List<String> keys = new ArrayList<>();
        keys.add(key);
        // 脚本里的ARGV参数
        List<String> args = new ArrayList<>();
        args.add(Integer.toString(num));

        RedisScript<Long> SET_NX_WITH_EXPIRE_SCRIPT = new DefaultRedisScript<>(STOCK_LUA, Long.class);

        Long result = redisTemplate.execute(SET_NX_WITH_EXPIRE_SCRIPT,redisTemplate.getStringSerializer(),new GenericToStringSerializer<>(Long.class),keys,args);
        return result;
    }


}
