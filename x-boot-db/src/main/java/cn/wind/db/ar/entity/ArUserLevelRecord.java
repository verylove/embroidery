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
 * 用户level新增记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_level_record")
public class ArUserLevelRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 添加的level值
     */
    private Integer num;
    /**
     * 1-活跃 2-魅力 3-财富
     */
    private Integer category;
    /**
     * 1-签到 2-停留3分钟 3-停留5分钟 4-发布动态 5-被关注 6-被点赞 7-充值
     */
    private Integer type;


}
