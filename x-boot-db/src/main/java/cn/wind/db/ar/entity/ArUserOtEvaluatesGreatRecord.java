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
 * 纹纹达人用户评论点赞记录表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_ot_evaluates_great_record")
public class ArUserOtEvaluatesGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_ot_evaluate`ID
     */
    private Long otEvaluateId;
    /**
     * `cx_ar_user`点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user` 评论者
     */
    private Long replyId;


}
