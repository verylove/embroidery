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
 * 纹纹达人图片数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_ot_pic")
public class ArUserOtPic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * `cx_ar_user_ot_tattoo`ID
     */
    private Long otTattooId;
    /**
     * 图片
     */
    private String img;


}
