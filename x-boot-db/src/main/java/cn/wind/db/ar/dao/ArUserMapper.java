package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * App用户表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-06
 */
public interface ArUserMapper extends BaseMapper<ArUser> {

    String RESULT_COLUMN = "id,account,phone,password,icon,sex,profile,identity,province,city,county,per_address,work_num"+
            ",work_place,sign_id,name_id,store_id,sign_status,name_status,store_status,wx_open_id,qq_open_id,per_level"+
            ",active_num,charm_num,wealth_num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user where phone = #{phone}")
    ArUser findOneByPhone(@Param("phone") String phone);

    @Select("select "+RESULT_COLUMN+" from cx_ar_user where wx_open_id = #{openId}")
    ArUser finOneByWxOpenId(@Param("openId")String openId);

    @Select("select "+RESULT_COLUMN+" from cx_ar_user where qq_open_id = #{openId}")
    ArUser findOneByQqOpenId(String openId);
}
