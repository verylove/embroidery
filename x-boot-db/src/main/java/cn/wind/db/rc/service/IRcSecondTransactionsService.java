package cn.wind.db.rc.service;

import cn.wind.db.rc.entity.RcSecondTransactions;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 二手市场数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IRcSecondTransactionsService extends IService<RcSecondTransactions> {

    Page<RcSecondTransactions> findAllByConditons(Page page, Map<String, Object> map);

    Page<RcSecondTransactions> findByCoordinates(Page page, Map<String, Object> map);

    RcSecondTransactions findOneByConditions(Map<String, Object> map);

    Page<RcSecondTransactions> findAllRcByCondition(Page page, Map<String, Object> map);
}
