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
 * 用户特价纹身评论数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sp_evaluates")
public class ArUserSpEvaluates extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_sp_tattoo`ID
     */
    private Long specialTattooId;
    /**
     * `cx_ar_user`ID评论者ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID特价纹身作者ID
     */
    private Long authorId;
    /**
     * 类型 1-评论特价纹身 2-评论别人的评论
     */
    private Integer type;
    /**
     * 评论等级 1-评论特价纹身 其他评论别人品论
     */
    private Integer level;
    /**
     * type为1填0；type为2时为评论人ID
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

    @TableField(exist=false)
    private ArUser user;


}
