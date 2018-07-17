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
 * 用户话题贴吧评论点赞记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_tb_evaluates_great_record")
public class ArUserTbEvaluatesGreatRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_tb_evaluate`ID
     */
    private Long tbEvaluateId;
    /**
     * `cx_ar_user`ID 点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user `ID评论者
     */
    private Long replyId;


}
