package cn.wind.db.rc.service;

import cn.wind.db.rc.entity.RcSecondPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 二手市场图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IRcSecondPicService extends IService<RcSecondPic> {

    List<RcSecondPic> findAllBySecondTransactId(Long id);
}
