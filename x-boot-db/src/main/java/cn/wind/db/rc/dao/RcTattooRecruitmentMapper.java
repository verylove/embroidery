package cn.wind.db.rc.dao;

import cn.wind.db.rc.entity.RcTattooRecruitment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 纹身师招聘信息发布数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface RcTattooRecruitmentMapper extends BaseMapper<RcTattooRecruitment> {

    List<RcTattooRecruitment> findByCoordinates(Pagination page, Map<String, Object> map);

    List<RcTattooRecruitment> findAllByConditons(Pagination page, Map<String, Object> map);

    RcTattooRecruitment findOneByConditions(Map<String, Object> map);

    List<RcTattooRecruitment> findAllRcByCondition(Pagination page, Map<String, Object> map);
}
