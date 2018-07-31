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
 * 店铺室内环境图集数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_store_pic")
public class ArStorePic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * url
     */
    private String img;
    /**
     * `cx_ar_store_aduit`ID
     */
    private Long storeId;


}
