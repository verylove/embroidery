package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserZxTattoo;
import cn.wind.db.ar.service.IArUserZxTattooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 10:53
 * @Description:
 */
@Service
public class WebZxTattooManage {

    @Autowired
    private IArUserZxTattooService zxTattooService;

    @Transactional
    public ApiRes zxTattooDelete(Long zxTattooId) {
        ArUserZxTattoo zxTattoo = zxTattooService.selectById(zxTattooId);
        if(zxTattoo != null){
            zxTattoo.setStatus(0);
            zxTattooService.updateById(zxTattoo);
        }
        return ApiRes.Custom().success();
    }
}
