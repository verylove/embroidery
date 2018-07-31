package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserBank;
import cn.wind.db.ar.dao.ArUserBankMapper;
import cn.wind.db.ar.service.IArUserBankService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户银行卡信息数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Service
public class ArUserBankServiceImpl extends ServiceImpl<ArUserBankMapper, ArUserBank> implements IArUserBankService {

    @Override
    public List<ArUserBank> findAllByConditions(Map<String, Object> map) {
        return this.baseMapper.findAllByConditions(map);
    }

    @Override
    public ArUserBank findOneByConditons(Map<String, Object> map) {
        return this.baseMapper.findOneByConditons(map);
    }
}
