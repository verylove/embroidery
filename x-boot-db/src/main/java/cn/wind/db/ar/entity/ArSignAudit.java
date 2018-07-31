package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户签约保障申请数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_sign_audit")
public class ArSignAudit extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 说明
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 申请内容
     */
    private String content;
    /**
     * 1-待审批 2-审批通过 3-审批拒绝
     */
    private Integer status;


}
