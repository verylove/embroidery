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
 * 用户消息数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_message")
public class ArUserMessage extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 执行操作的人 -1代表系统
     */
    private Long fromUserId;
    /**
     * 1-系统消息 2-我的评论 3-我的赞 4-我的关注 5-我的私信
     */
    private Integer type;
    /**
     * 收到消息推送的人
     */
    private Long toUserId;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 1-特价纹身 2-图库找图 3-贴吧话题 4-纹纹达人 5-动态 6-作品 7-特价纹身评论 8-图库找图评论 9-贴吧话题评论 10-纹纹达人评论 11-动态评论 12-作品评论
     */
    private Integer category;
    /**
     * 评论ID
     */
    private Long evaluateId;
    /**
     * 额外内容
     */
    private String extraContent;
    /**
     * 额外对应ID
     */
    private Long extraId;


}
