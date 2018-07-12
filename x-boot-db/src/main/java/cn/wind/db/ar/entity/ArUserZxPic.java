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
 * 用户纹身咨询图片数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_zx_pic")
public class ArUserZxPic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_zx_tattoo`纹身咨询ID
     */
    private Long zxTattooId;
    /**
     * 图片地址
     */
    private String img;


}
