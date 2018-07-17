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
 * 用户贴吧话题点赞次数数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_tb_great_num")
public class ArUserTbGreatNum extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_tb_topic`ID
     */
    private Long tbTopicId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作者ID
     */
    private Long authorId;
    /**
     * 点赞次数
     */
    private Integer num;


}
