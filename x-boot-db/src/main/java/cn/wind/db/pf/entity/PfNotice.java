package cn.wind.db.pf.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 平台通知
 * </p>
 *
 * @author xukk
 * @since 2018-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_pf_notice")
public class PfNotice extends AuditEntity {

    private static final long serialVersionUID = 1L;

    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     *  轮播排序（可用于置顶）
     */
    private Integer sortOrder;
    /**
     * 是否已读
     */
    private Integer readed;
    @TableLogic
    private Integer delFlag;


}
