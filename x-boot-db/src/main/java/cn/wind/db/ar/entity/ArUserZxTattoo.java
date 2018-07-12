package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户纹身咨询数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_zx_tattoo")
public class ArUserZxTattoo extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 意向图案
     */
    private String intention;
    /**
     * 纹身部位
     */
    private String parts;
    /**
     * 其他说明
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 微信
     */
    private String microLetter;
    /**
     * 电话
     */
    private String phone;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市
     */
    private Long city;

    @TableField(exist = false)
    private ArUser user;


}
