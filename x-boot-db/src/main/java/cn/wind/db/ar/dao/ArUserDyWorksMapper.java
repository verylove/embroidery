package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDyWorks;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作品/动态数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDyWorksMapper extends BaseMapper<ArUserDyWorks> {

    List<ArUserDyWorks> findAllStoreByConditions(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllArtistByConditions(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllDyFocusByConditions(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllCircleWorkOrderByTime(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllCircleWorkOrderByGreatNum(Pagination page, Map<String, Object> map);

    ArUserDyWorks findOneByConditions(Map<String, Object> map);

    List<ArUserDyWorks> findAllStoreWorksForManager(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllStoreWorksForPerson(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllPerDynamic(Pagination page, Map<String, Object> map);

    List<ArUserDyWorks> findAllDyByCondition(Pagination page, Map<String, Object> map);
}
