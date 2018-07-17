package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户贴吧话题数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_tb_topic")
public class ArUserTbTopic extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 留言数
     */
    private Long messageNum=0L;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 1-贴吧 2-话题
     */
    private Integer type;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist = false)
    private ArUser user;


}
