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
 * 文章公众号数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_sr_article")
public class SrArticle extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文章名
     */
    private String name;
    /**
     * 说明
     */
    private String describtion;
    /**
     * 补充
     */
    private String remark;
    /**
     * 封面图片
     */
    private String coverImg;
    /**
     * url
     */
    private String url;
    /**
     * 类型 1-纹身素材 2-纹身培训 3-公众号 4-签约纹身店 5-失效
     */
    private Integer type;


}
