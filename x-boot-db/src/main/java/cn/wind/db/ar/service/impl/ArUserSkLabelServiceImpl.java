package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSkLabel;
import cn.wind.db.ar.dao.ArUserSkLabelMapper;
import cn.wind.db.ar.service.IArUserSkLabelService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图库找图标签数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Service
public class ArUserSkLabelServiceImpl extends ServiceImpl<ArUserSkLabelMapper, ArUserSkLabel> implements IArUserSkLabelService {

    @Override
    public List<ArUserSkLabel> findAllByIdsIn(List<Long> labelIds) {
        return this.baseMapper.findAllByIdsIn(labelIds);
    }

    @Override
    public Page<ArUserSkLabel> findAllLabelByCondition(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllLabelByCondition(page,map));
    }

    @Override
    public List<ArUserSkLabel> findAllParentLabel() {
        return this.baseMapper.findAllParentLabel();
    }
}
