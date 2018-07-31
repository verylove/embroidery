package cn.wind.db.rc.entity;

import java.util.Date;

import cn.wind.db.ar.entity.ArUser;
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
 * 纹身师招聘信息发布数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_rc_tattoo_recruitment")
public class RcTattooRecruitment extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 性别要求 0-无 1-男 2-女
     */
    private Integer sex=0;
    /**
     * 入职时间
     */
    private Date inductionTime;
    /**
     * 入职条件
     */
    private String inductionConditions;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 发布地点ID
     */
    private Long cityWhere;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist = false)
    private ArUser user;

    @TableField(exist=false)
    private Long distance;

}
