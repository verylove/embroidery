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
 * 用户贴吧话题评论数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_tb_evaluates")
public class ArUserTbEvaluates extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_tb_topic`ID
     */
    private Long tbTopicId;
    /**
     * `cx_ar_user`ID评论者ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID特价纹身作者ID
     */
    private Long authorId;
    /**
     * 类型 1-评论话题贴吧 2-评论别人的评论
     */
    private Integer type;
    /**
     * 评论等级 1-评论话题贴吧 其他评论别人评论
     */
    private Integer level;
    /**
     * type为1填0；type为2时为评论ID
     */
    private Long replyId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 回复条数 ，即此层的回复条数
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

    @TableField(exist = false)
    private ArUser user;


}
