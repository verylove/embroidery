package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 19:56
 * @Description:
 */
@Data
public class ArUserOtEvaluatesVo {
    private Long id;

    /**
     * `cx_ar_user_ot_tattoo`ID
     */
    private Long otTattooId;
    /**
     * `cx_ar_user`ID 点赞人ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 图库找图作者ID
     */
    private Long authorId;
    /**
     * 类型 1-评论纹纹达人 2-评论别人的评论
     */
    private Integer type;
    /**
     * 评论等级 1-评论纹纹达人 2-其他
     */
    private Integer level;
    /**
     * type为1填0，其他填所回复的评论ID
     */
    private Long replyId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 该评论的回复条数 默认0
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
