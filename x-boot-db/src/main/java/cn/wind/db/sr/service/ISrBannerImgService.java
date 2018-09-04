package cn.wind.db.sr.service;

import cn.wind.db.sr.entity.SrBannerImg;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface ISrBannerImgService extends IService<SrBannerImg> {

    List<SrBannerImg> findAllByCategory(Integer category);

    Page<SrBannerImg> findAllByConditions(Page page, Map<String, Object> map);
}
