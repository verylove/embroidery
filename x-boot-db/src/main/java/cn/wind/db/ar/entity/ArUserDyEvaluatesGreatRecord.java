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
 * 动态作品用户评论点赞记录表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_dy_evaluates_great_record")
public class ArUserDyEvaluatesGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_dy_evaluate`ID
     */
    private Long dyEvaluateId;
    /**
     * `cx_ar_user`点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user` 评论者
     */
    private Long replyId;


}
