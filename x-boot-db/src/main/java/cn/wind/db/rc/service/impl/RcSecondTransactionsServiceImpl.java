package cn.wind.db.rc.service.impl;

import cn.wind.db.rc.entity.RcSecondTransactions;
import cn.wind.db.rc.dao.RcSecondTransactionsMapper;
import cn.wind.db.rc.service.IRcSecondTransactionsService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 二手市场数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class RcSecondTransactionsServiceImpl extends ServiceImpl<RcSecondTransactionsMapper, RcSecondTransactions> implements IRcSecondTransactionsService {

    @Override
    public Page<RcSecondTransactions> findAllByConditons(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditons(page,map));
    }

    @Override
    public Page<RcSecondTransactions> findByCoordinates(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findByCoordinates(page,map));
    }

    @Override
    public RcSecondTransactions findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public Page<RcSecondTransactions> findAllRcByCondition(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllRcByCondition(page,map));
    }
}
