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
 * 纹纹达人数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_ot_tattoo")
public class ArUserOtTattoo extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容
     */
    private String content;
    /**
     * 观看人数
     */
    private Integer watchNum=0;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 评价数
     */
    private Long messageNum=0L;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;
    /**
     * 城市
     */
    private Long city;

    @TableField(exist = false)
    private ArUser user;

}
