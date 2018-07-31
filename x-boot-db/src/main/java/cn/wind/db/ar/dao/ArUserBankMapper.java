package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserBank;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户银行卡信息数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface ArUserBankMapper extends BaseMapper<ArUserBank> {

    List<ArUserBank> findAllByConditions(Map<String, Object> map);

    ArUserBank findOneByConditons(Map<String, Object> map);
}
