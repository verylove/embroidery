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
 * 用户店铺认证申请数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_store_aduit")
public class ArStoreAduit extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 详细地址
     */
    private String storeAddress;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 描述
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
     * 状态 1待审批 2-审批通过 3-审批拒绝
     */
    private Integer status;
    /**
     * 营业执照
     */
    private String busLicense;


}
