package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserBank;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户银行卡信息数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface IArUserBankService extends IService<ArUserBank> {

    List<ArUserBank> findAllByConditions(Map<String, Object> map);

    ArUserBank findOneByConditons(Map<String, Object> map);
}
