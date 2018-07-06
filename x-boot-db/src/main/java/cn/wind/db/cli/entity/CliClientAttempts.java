package cn.wind.db.cli.entity;

import cn.wind.mybatis.common.IdEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 会员用户尝试登陆次数记录表
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_cli_client_attempts")
public class CliClientAttempts extends IdEntity {

    private static final long serialVersionUID = 1L;

    private String username;
    private Integer attempts;
    private Date lastModified;


}
