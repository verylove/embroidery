package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkLabel;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图库找图标签数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkLabelService extends IService<ArUserSkLabel> {

    List<ArUserSkLabel> findAllByIdsIn(List<Long> labelIds);

    Page<ArUserSkLabel> findAllLabelByCondition(Page page, Map<String, Object> map);

    List<ArUserSkLabel> findAllParentLabel();
}
