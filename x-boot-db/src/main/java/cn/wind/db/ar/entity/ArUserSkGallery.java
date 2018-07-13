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
 * 图库找图数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sk_gallery")
public class ArUserSkGallery extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容描述
     */
    private String content;
    /**
     * 标签
     */
    private Long label;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 观看次数
     */
    private Integer watchNum=0;
    /**
     * 发布位置
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 留言数
     */
    private Long messageNum=0L;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist = false)
    private ArUser user;

}
