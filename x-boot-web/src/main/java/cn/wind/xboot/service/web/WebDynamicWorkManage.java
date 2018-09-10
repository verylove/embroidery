package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserDyEvaluates;
import cn.wind.db.ar.entity.ArUserDyWorks;
import cn.wind.db.ar.service.IArUserDyEvaluatesService;
import cn.wind.db.ar.service.IArUserDyWorksService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 19:16
 * @Description:
 */
@Service
public class WebDynamicWorkManage {

    @Autowired
    private IArUserDyWorksService dyWorksService;
    @Autowired
    private IArUserDyEvaluatesService dyEvaluatesService;

    @Transactional
    public ApiRes circleDelete(Long dyWorksId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyWorksId);
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);

        if(dyWorks != null){
            dyWorks.setStatus(0);
            dyWorksService.updateById(dyWorks);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes circleEvaluatesDelete(Long dyEvaluateId) {
        ArUserDyEvaluates dyEvaluates = dyEvaluatesService.selectById(dyEvaluateId);
        if(dyEvaluates != null){
            if (dyEvaluates.getStatus() == 1){
                dyEvaluates.setStatus(0);
                dyEvaluatesService.updateById(dyEvaluates);

                Map<String,Object> map = Maps.newHashMap();
                map.put("dyWorksId",dyEvaluates.getDyWorksId());
                ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);
                dyWorks.setMessageNum(dyWorks.getMessageNum()-1);
                dyWorksService.updateById(dyWorks);
            }
        }
        return ApiRes.Custom().success();
    }
}
