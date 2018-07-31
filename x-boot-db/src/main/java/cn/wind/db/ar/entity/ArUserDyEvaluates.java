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
 * 动态作品用户评价数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_dy_evaluates")
public class ArUserDyEvaluates extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_dy_works`ID
     */
    private Long dyWorksId;
    /**
     * `cx_ar_user`ID 点赞人ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 动态作品作者ID
     */
    private Long authorId;
    /**
     * 类型 1-评论动态作品 2-评论别人的评论
     */
    private Integer type;
    /**
     * 评论等级 1-评论动态作品 2-其他
     */
    private Integer level;
    /**
     * type为1填0，其他填所回复的评论ID
     */
    private Long replyId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 该评论的回复条数 默认0
     */
    private Integer replyNum=0;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist=false)
    private ArUser user;


}
