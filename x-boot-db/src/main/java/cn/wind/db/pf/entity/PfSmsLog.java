package cn.wind.db.pf.entity;

import java.util.Date;
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
 * 
 * </p>
 *
 * @author xukk
 * @since 2018-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_pf_sms_log")
public class PfSmsLog extends AuditEntity {

    private static final long serialVersionUID = 1L;

    private String phoneNum;
    private String templateCode;
    private String content;
    private Integer sendStatus;
    private Date curSendTime;
    private Date sendTime;
    private Date receiveTime;
    private Integer outId;
    private String errCode;
    @TableLogic
    private Integer delFlag;


}
