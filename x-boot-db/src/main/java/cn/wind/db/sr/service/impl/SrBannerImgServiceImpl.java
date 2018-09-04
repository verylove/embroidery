package cn.wind.db.sr.service.impl;

import cn.wind.db.sr.entity.SrBannerImg;
import cn.wind.db.sr.dao.SrBannerImgMapper;
import cn.wind.db.sr.service.ISrBannerImgService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Service
public class SrBannerImgServiceImpl extends ServiceImpl<SrBannerImgMapper, SrBannerImg> implements ISrBannerImgService {

    @Override
    public List<SrBannerImg> findAllByCategory(Integer category) {
        return this.baseMapper.findAllByCategory(category);
    }

    @Override
    public Page<SrBannerImg> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }
}
