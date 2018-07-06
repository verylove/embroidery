package cn.wind.db.pf.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 短信模板
 * </p>
 *
 * @author xukk
 * @since 2018-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_pf_sms")
public class PfSms extends AuditEntity {

    private static final long serialVersionUID = 1L;
    private String name;
    private String title;
    private String templateCode;
    private String content;
    /**
     * 1标识删除   0否
     */
    @TableLogic
    private Integer delFlag;


}
