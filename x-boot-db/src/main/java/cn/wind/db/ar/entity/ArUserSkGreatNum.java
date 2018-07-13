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
 * 图库找图点赞次数数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sk_great_num")
public class ArUserSkGreatNum extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_sk_gallery`ID
     */
    private Long seekGalleryId;
    /**
     * `cx_ar_user`ID 点赞人ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 作者ID
     */
    private Long authorId;
    /**
     * 点赞次数
     */
    private Integer num;


}
