package cn.wind.db.sr.service.impl;

import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.dao.SrAreaMapper;
import cn.wind.db.sr.service.ISrAreaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 中国区域数据表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-07-06
 */
@Service
public class SrAreaServiceImpl extends ServiceImpl<SrAreaMapper, SrArea> implements ISrAreaService {

    @Override
    public List<SrArea> findAllByConditions(Map<String, Object> map) {
        return this.baseMapper.findAllByConditions(map);
    }
}
