package cn.wind.db.cli.dao;

import cn.wind.db.cli.entity.CliClient;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 平台会员表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
public interface CliClientMapper extends BaseMapper<CliClient> {
    /**
     * 公共返回字段
     */
    String RESULT_COLUMN = "id,username,password,account_non_expired,credentials_non_expired,account_non_Locked,enabled,status" +
            ",create_time,create_by,modify_time,modify_by,lock_time,private_key,public_key";

    /**
     * 更新accountNonLocked属性
     *
     * @param accountNonLocked
     * @param username
     */
    @Update("update t_cli_client set accountNonLocked=:accountNonLocked where username=#{username} ")
    void updateForAccountNonLocked(@Param("accountNonLocked") Boolean accountNonLocked, @Param("username") String username);

    /**
     * fetch data 根据手机号获取用户记录
     *
     * @param username 用户手机号
     * @return CliClient
     */
    @Select("select " + RESULT_COLUMN + " from t_cli_client where username=#{username}")
    CliClient findByUsername(@Param("username") String username);

}
