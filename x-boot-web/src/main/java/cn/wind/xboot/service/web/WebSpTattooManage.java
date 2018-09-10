package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserSpEvaluates;
import cn.wind.db.ar.entity.ArUserSpPic;
import cn.wind.db.ar.entity.ArUserSpTattoo;
import cn.wind.db.ar.service.IArUserSpEvaluatesService;
import cn.wind.db.ar.service.IArUserSpPicService;
import cn.wind.db.ar.service.IArUserSpTattooService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.vo.web.ar.spTattoo.AppArSpTattooVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 16:31
 * @Description:
 */
@Service
public class WebSpTattooManage {

    @Autowired
    private IArUserSpTattooService spTattooService;
    @Autowired
    private ISrAreaService srAreaService;
    @Autowired
    private IArUserSpPicService picService;
    @Autowired
    private IArUserSpEvaluatesService spEvaluatesService;

    @Transactional
    public ApiRes spTattooDetail(Long spTattooId) {
        AppArSpTattooVo vo = new AppArSpTattooVo();
        ArUserSpTattoo spTattoo = spTattooService.findOneById(spTattooId);
        if(spTattoo == null){
            return ApiRes.Custom().failure("该特价纹身不存在");
        }
        BeanUtils.copyProperties(spTattoo,vo);
        vo.setAccount(spTattoo.getUser().getAccount());

        SrArea area = srAreaService.selectById(spTattoo.getCity());
        vo.setCityName(area.getName());

        List<ArUserSpPic> pics = picService.findAllByTattoId(spTattoo.getId());
        vo.setPics(pics);
        return ApiRes.Custom().success().addData(vo);
    }

    @Transactional
    public ApiRes spTattooDelete(Long spTattooId) {
        ArUserSpTattoo spTattoo = spTattooService.findOneById(spTattooId);
        if(spTattoo != null){
            spTattoo.setStatus(0);
            spTattooService.updateById(spTattoo);
        }

        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes spTattooEvaluateDelete(Long spEvaluateId) {
        ArUserSpEvaluates spEvaluates = spEvaluatesService.selectById(spEvaluateId);
        if(spEvaluates != null){
            if(spEvaluates.getStatus() == 1){
                spEvaluates.setStatus(0);
                spEvaluatesService.updateById(spEvaluates);

                ArUserSpTattoo spTattoo = spTattooService.findOneById(spEvaluates.getSpecialTattooId());
                spTattoo.setMessageNum(spTattoo.getMessageNum()-1);
                spTattooService.updateById(spTattoo);
            }
        }
        return ApiRes.Custom().success();
    }
}
