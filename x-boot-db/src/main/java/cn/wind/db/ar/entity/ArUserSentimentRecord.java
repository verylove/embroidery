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
 * 用户人气新增记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sentiment_record")
public class ArUserSentimentRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 添加的人气
     */
    private Integer num;
    /**
     * 1-上传作品集 2-作品集点赞 3-作品集分享 4-圈子点赞 5-粉丝新增
     */
    private Integer type;


}
