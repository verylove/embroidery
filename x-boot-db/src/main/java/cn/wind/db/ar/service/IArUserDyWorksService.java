package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDyWorks;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 作品/动态数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDyWorksService extends IService<ArUserDyWorks> {

    Page<ArUserDyWorks> findAllStoreByConditions(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllArtistByConditions(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllDyFocusByConditions(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllCircleWorkOrderByTime(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllCircleWorkOrderByGreatNum(Page page, Map<String, Object> map);

    ArUserDyWorks findOneByConditions(Map<String, Object> map);

    Page<ArUserDyWorks> findAllStoreWorksForManager(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllStoreWorksForPerson(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllPerDynamic(Page page, Map<String, Object> map);

    Page<ArUserDyWorks> findAllDyByCondition(Page page, Map<String, Object> map);
}
