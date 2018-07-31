package cn.wind.db.ml.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户收获地址数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ml_user_address")
public class MlUserAddress extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收件人
     */
    private String name;
    /**
     * 省ID
     */
    private Long province;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 县区ID
     */
    private Long county;
    /**
     * 地址详情
     */
    private String adressDetail;
    /**
     * 收件人手机号
     */
    private String phone;
    /**
     * 1-默认 0-不默认
     */
    private Integer type=0;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;


}
