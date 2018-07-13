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
 * 图库找图用户评论点赞记录表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sk_evaluates_great_record")
public class ArUserSkEvaluatesGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_sk_evaluate`ID
     */
    private Long skEvaluateId;
    /**
     * `cx_ar_user`点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user` 评论者
     */
    private Long replyId;


}
