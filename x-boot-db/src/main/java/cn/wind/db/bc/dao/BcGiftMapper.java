package cn.wind.db.bc.dao;

import cn.wind.db.bc.entity.BcGift;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 直播礼物数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface BcGiftMapper extends BaseMapper<BcGift> {

    String RESULT_COLUMN = "id,name,pic,worth,status,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_bc_gift where status = 1")
    List<BcGift> findAllOn();
}
