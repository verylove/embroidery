package cn.wind.xboot.vo.app.rc;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 14:53
 * @Description:
 */
@Data
public class RcTattooRecruitVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 性别要求 0-无 1-男 2-女
     */
    private Integer sex=0;
    /**
     * 入职时间
     */
    private Date inductionTime;
    /**
     * 入职条件
     */
    private String inductionConditions;
    /**
     * 发布地点ID
     */
    private Long cityWhere;

    private Date createTime;

    private Date modifyTime;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;
}
