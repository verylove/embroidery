package cn.wind.xboot.service.app;

import cn.wind.db.rc.entity.RcTattooRecruitment;
import cn.wind.db.rc.service.IRcTattooRecruitmentService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.dto.app.rc.rcRecruitMentDto;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 14:44
 * @Description: 招聘管理
 */
@Service
public class CXRcRecruitManage {

    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IRcTattooRecruitmentService rcTattooRecruitmentService;

    /**
     * 招聘发布
     * @param dto
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void publishInRecruit(rcRecruitMentDto dto, Long userId)throws Exception {
        //1.插入招聘信息
        RcTattooRecruitment recruitment = new RcTattooRecruitment();
        BeanUtils.copyProperties(dto,recruitment);
        recruitment.setUserId(userId);
        SrArea area = areaService.selectById(dto.getCity());
        String center = area.getCenter();
        if(Strings.isNullOrEmpty(center)){
            throw new RuntimeException();
        }
        String[] coordinates = center.split(",");
        recruitment.setLongitude(coordinates[0]);
        recruitment.setLatitude(coordinates[1]);
        rcTattooRecruitmentService.insert(recruitment);
    }
}
