package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserTbEvaluates;
import cn.wind.db.ar.entity.ArUserTbTopic;
import cn.wind.db.ar.service.IArUserTbEvaluatesService;
import cn.wind.db.ar.service.IArUserTbTopicService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 17:45
 * @Description:
 */
@Service
public class WebTbTopicManage {

    @Autowired
    private IArUserTbTopicService tbTopicService;
    @Autowired
    private IArUserTbEvaluatesService tbEvaluatesService;

    @Transactional
    public ApiRes tbTopicDelete(Long tbTopicId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbTopicId);
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);

        if(tbTopic != null){
            tbTopic.setStatus(0);
            tbTopicService.updateById(tbTopic);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes tbTopicEvaluateDelete(Long tbTopicEvaluateId) {
        ArUserTbEvaluates tbEvaluates = tbEvaluatesService.selectById(tbTopicEvaluateId);
        if(tbEvaluates != null){
            if(tbEvaluates.getStatus() == 1){
                tbEvaluates.setStatus(0);
                tbEvaluatesService.updateById(tbEvaluates);

                Map<String,Object> map = Maps.newHashMap();
                map.put("tbTopicId",tbEvaluates.getTbTopicId());
                ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);
                tbTopic.setMessageNum(tbTopic.getMessageNum()-1);
                tbTopicService.updateById(tbTopic);
            }
        }
        return ApiRes.Custom().success();
    }
}
