package cn.wind.db.sr.service;

import cn.wind.db.sr.entity.SrBannerImg;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 轮播图数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface ISrBannerImgService extends IService<SrBannerImg> {

    List<SrBannerImg> findAllByType(Integer type);
}
