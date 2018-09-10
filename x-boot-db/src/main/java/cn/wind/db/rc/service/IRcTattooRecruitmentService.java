package cn.wind.db.rc.service;

import cn.wind.db.rc.entity.RcTattooRecruitment;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 纹身师招聘信息发布数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IRcTattooRecruitmentService extends IService<RcTattooRecruitment> {

    Page<RcTattooRecruitment> findByCoordinates(Page page, Map<String, Object> map);

    Page<RcTattooRecruitment> findAllByConditons(Page page, Map<String, Object> map);

    RcTattooRecruitment findOneByConditions(Map<String, Object> map);

    Page<RcTattooRecruitment> findAllRcByCondition(Page page, Map<String, Object> map);
}
