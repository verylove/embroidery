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
 * 轮播图数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_sr_banner_img")
public class SrBannerImg extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图
     */
    private String img;
    /**
     * 轮播图位置 1-首页 2-商城
     */
    private Integer category;
    /**
     * 轮播图类型 1-URL
     */
    private Integer type;
    /**
     * 轮播图key值
     */
    private String imgKey;


}
