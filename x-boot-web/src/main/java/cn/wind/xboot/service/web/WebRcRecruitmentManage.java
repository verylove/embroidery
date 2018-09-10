package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.rc.entity.RcTattooRecruitment;
import cn.wind.db.rc.service.IRcTattooRecruitmentService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/5 17:06
 * @Description:
 */
@Service
public class WebRcRecruitmentManage {

    @Autowired
    private IRcTattooRecruitmentService recruitmentService;

    @Transactional
    public ApiRes recruitmentDelete(Long recruitId) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("recruitId",recruitId);
        RcTattooRecruitment recruitment = recruitmentService.findOneByConditions(map);

        if(recruitment != null){
            recruitment.setStatus(0);
            recruitmentService.updateById(recruitment);
        }
        return ApiRes.Custom().success();
    }
}
