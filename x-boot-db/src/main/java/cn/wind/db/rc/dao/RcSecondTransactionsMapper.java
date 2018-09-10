package cn.wind.db.rc.dao;

import cn.wind.db.rc.entity.RcSecondTransactions;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二手市场数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface RcSecondTransactionsMapper extends BaseMapper<RcSecondTransactions> {

    List<RcSecondTransactions> findAllByConditons(Pagination page, Map<String, Object> map);

    List<RcSecondTransactions> findByCoordinates(Pagination page, Map<String, Object> map);

    RcSecondTransactions findOneByConditions(Map<String, Object> map);

    List<RcSecondTransactions> findAllRcByCondition(Pagination page, Map<String, Object> map);
}
