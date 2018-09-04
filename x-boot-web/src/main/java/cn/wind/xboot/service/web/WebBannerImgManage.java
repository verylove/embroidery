package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sr.entity.SrBannerImg;
import cn.wind.db.sr.service.ISrBannerImgService;
import cn.wind.xboot.vo.web.sr.AppSrBannerImgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 14:42
 * @Description:
 */
@Service
public class WebBannerImgManage {

    @Autowired
    private ISrBannerImgService bannerImgService;

    @Transactional
    public ApiRes homeBannerUpdate(AppSrBannerImgVo bannerImgVo) {
        if(bannerImgVo.getId() == null || bannerImgVo.getId().compareTo(0L) <= 0){//新增
            SrBannerImg bannerImg = new SrBannerImg();
            bannerImg.setCategory(1);
            bannerImg.setType(1);
            bannerImg.setImg(bannerImgVo.getImg());
            bannerImg.setImgKey(bannerImgVo.getImgKey());
            bannerImgService.insert(bannerImg);
        }else {
            SrBannerImg bannerImg = bannerImgService.selectById(bannerImgVo.getId());
            if(bannerImg == null){
                return ApiRes.Custom().failure("该轮播图不存在");
            }
            bannerImg.setImgKey(bannerImgVo.getImgKey());
            bannerImg.setImg(bannerImgVo.getImg());
            bannerImgService.updateById(bannerImg);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes homeBannerDelete(Long bannerImgId) {
        SrBannerImg bannerImg = bannerImgService.selectById(bannerImgId);
        if(bannerImg != null){
            bannerImgService.deleteById(bannerImgId);
        }
        return ApiRes.Custom().success();
    }
}
