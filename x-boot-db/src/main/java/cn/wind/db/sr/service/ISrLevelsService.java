package cn.wind.db.sr.service;

import cn.wind.db.sr.entity.SrLevels;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 等级数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
public interface ISrLevelsService extends IService<SrLevels> {

    SrLevels findOneByLevel(Integer perLevel);
}
