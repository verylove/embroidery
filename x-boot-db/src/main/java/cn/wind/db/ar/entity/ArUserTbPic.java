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
 * 用户贴吧话题图片数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_tb_pic")
public class ArUserTbPic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_tb_topic`ID
     */
    private Long tbTopicId;
    /**
     * 图片
     */
    private String img;


}
