package cn.wind.xboot.mall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/27 14:27
 * @Description:
 */
@Service
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * setNx
     *
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }
    /**
     * @param key        锁
     * @param waitTime   等待时间 毫秒
     * @param expireTime 超时时间  毫秒
     * @return
     */
    public Boolean lock(String key, Long waitTime, Long expireTime) {
        String vlaue = UUID.randomUUID().toString().replaceAll("-", "");
        Boolean flag = setNx(key, vlaue);
        //尝试获取锁 成功返回
        if (flag) {
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            return flag;
        } else {
            //失败
            //现在时间
            long newTime = System.currentTimeMillis();
            //等待过期时间
            long loseTime = newTime + waitTime;
            //不断尝试获取锁成功返回
            while (System.currentTimeMillis() < loseTime) {
                Boolean testFlag = setNx(key, vlaue);
                if (testFlag) {
                    redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
                    return testFlag;
                }
                //休眠100毫秒
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    /**
     * @param key
     * @return
     */
    public Boolean lock(String key) {
        return lock(key, 1000L, 60 * 1000L);
    }
    /**
     * @param key
     */
    public void unLock(String key) {
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
        }
    }


}
