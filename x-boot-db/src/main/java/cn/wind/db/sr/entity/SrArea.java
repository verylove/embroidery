package cn.wind.db.sr.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中国区域数据表
 * </p>
 *
 * @author xukk
 * @since 2018-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_sr_area")
public class SrArea extends AuditEntity {

    private static final long serialVersionUID = 1L;

    private String abbrName;
    private String alias;
    private String center;
    private String cityCode;
    private String code;
    private Integer level;
    private String name;
    private Integer status;


}
