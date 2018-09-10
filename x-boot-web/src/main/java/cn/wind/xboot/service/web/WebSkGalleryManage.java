package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserSkEvaluates;
import cn.wind.db.ar.entity.ArUserSkGallery;
import cn.wind.db.ar.entity.ArUserSkLabel;
import cn.wind.db.ar.service.IArUserSkEvaluatesService;
import cn.wind.db.ar.service.IArUserSkGalleryService;
import cn.wind.db.ar.service.IArUserSkLabelService;
import cn.wind.xboot.vo.web.ar.skGallery.AppArSkLabelVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 14:48
 * @Description:
 */
@Service
public class WebSkGalleryManage {

    @Autowired
    private IArUserSkGalleryService skGalleryService;
    @Autowired
    private IArUserSkEvaluatesService skEvaluatesService;
    @Autowired
    private IArUserSkLabelService labelService;

    @Transactional
    public ApiRes skGalleryDelete(Long skGalleryId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skGalleryId);
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);

        if(skGallery != null){
            skGallery.setStatus(0);
            skGalleryService.updateById(skGallery);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes skGalleryEvaluateDelete(Long skEvaluateId) {
        ArUserSkEvaluates skEvaluates = skEvaluatesService.selectById(skEvaluateId);
        if(skEvaluates != null){
            if(skEvaluates.getStatus() == 1){
                skEvaluates.setStatus(0);
                skEvaluatesService.updateById(skEvaluates);

                Map<String,Object> map = Maps.newHashMap();
                map.put("id",skEvaluates.getSeekGalleryId());
                ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);
                skGallery.setMessageNum(skGallery.getMessageNum()-1);
                skGalleryService.updateById(skGallery);
            }
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes skLabelUpdate(AppArSkLabelVo labelVo) {
        if(labelVo.getId() == null || labelVo.getId().compareTo(0L)<= 0){//新建
            ArUserSkLabel label = new ArUserSkLabel();
            label.setLevel(2);
            label.setName(labelVo.getName());
            label.setPid(labelVo.getPid());
            labelService.insert(label);
        }else {
            ArUserSkLabel label = labelService.selectById(labelVo.getId());
            label.setName(labelVo.getName());
            label.setPid(labelVo.getPid());
            labelService.updateById(label);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes skLabelDelete(Long skLabelId) {
        ArUserSkLabel skLabel = labelService.selectById(skLabelId);
        if(skLabel != null){
            Integer count = skGalleryService.countByLabel(skLabelId);
            if(count > 0){
                return ApiRes.Custom().failure("该标签下存在数据");
            }
            labelService.deleteById(skLabelId);
        }
        return ApiRes.Custom().success();
    }
}
