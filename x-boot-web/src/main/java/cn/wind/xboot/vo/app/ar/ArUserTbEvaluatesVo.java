package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 14:29
 * @Description:
 */
@Data
public class ArUserTbEvaluatesVo {
    private Long id;

    /**
     * `cx_ar_user_tb_topic`ID
     */
    private Long tbTopicId;
    /**
     * `cx_ar_user`ID评论者ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID特价纹身作者ID
     */
    private Long authorId;
    /**
     * 类型 1-评论话题贴吧 2-评论别人的评论
     */
    private Integer type;
    /**
     * 评论等级 1-评论话题贴吧 其他评论别人评论
     */
    private Integer level;
    /**
     * type为1填0；type为2时为评论ID
     */
    private Long replyId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 回复条数 ，即此层的回复条数
     */
    private Integer replyNum;
    /**
     * 点赞数
     */
    private Long greatNum;

    private Date createTime;

    private Date modifyTime;

    private String icon;//用户头像
    private String account;//用户名
    //评论该评论的人（随便选一人）
    private Long evaluterId;
    private String evaluterAccount;
}
