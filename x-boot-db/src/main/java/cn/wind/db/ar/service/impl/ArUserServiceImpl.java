package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.dao.ArUserMapper;
import cn.wind.db.ar.service.IArUserService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * App用户表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-06
 */
@Service
public class ArUserServiceImpl extends ServiceImpl<ArUserMapper, ArUser> implements IArUserService {

    @Override
    public ArUser findOneByPhone(String phone) {
        return this.baseMapper.findOneByPhone(phone);
    }

    @Override
    public ArUser findOneByWxOpenId(String openId) {
        return this.baseMapper.finOneByWxOpenId(openId);
    }

    @Override
    public ArUser findOneByQqOpenId(String openId) {
        return this.baseMapper.findOneByQqOpenId(openId);
    }

    @Override
    public List<ArUser> findAllByIdIn(List<Long> userIds) {
        return this.baseMapper.findAllByIdIn(userIds);
    }

    @Override
    public Page<ArUser> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }
}
