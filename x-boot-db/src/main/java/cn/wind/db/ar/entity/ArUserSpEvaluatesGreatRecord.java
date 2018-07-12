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
 * 用户特价纹身点赞记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sp_evaluates_great_record")
public class ArUserSpEvaluatesGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_sp_evaluate`ID
     */
    private Long spEvaluateId;
    /**
     * `cx_ar_user`ID 点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user `ID评论者
     */
    private Long replyId;


}
