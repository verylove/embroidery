package cn.wind.db.rc.service.impl;

import cn.wind.db.rc.entity.RcTattooRecruitment;
import cn.wind.db.rc.dao.RcTattooRecruitmentMapper;
import cn.wind.db.rc.service.IRcTattooRecruitmentService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 纹身师招聘信息发布数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class RcTattooRecruitmentServiceImpl extends ServiceImpl<RcTattooRecruitmentMapper, RcTattooRecruitment> implements IRcTattooRecruitmentService {

    @Override
    public Page<RcTattooRecruitment> findByCoordinates(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findByCoordinates(page,map));
    }

    @Override
    public Page<RcTattooRecruitment> findAllByConditons(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditons(page,map));
    }

    @Override
    public RcTattooRecruitment findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
