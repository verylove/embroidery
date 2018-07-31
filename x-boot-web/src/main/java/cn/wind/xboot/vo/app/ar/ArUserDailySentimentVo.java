package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/20 14:14
 * @Description: 个人人气详情
 */
@Data
public class ArUserDailySentimentVo {
    private Long id;

    private Long userId;

    private String Icon;

    private Long sentimentNum;

    /**
     * 当日的人气
     */
    private Long dailySentiment;
    /**
     * 当日被关注(粉丝)
     */
    private Long dailyFollow;
    /**
     * 当日新增作品集数
     */
    private Long dailyNewWorks;
    /**
     * 当日作品集被点赞次数
     */
    private Long dailyWorksGreat;
    /**
     * 当日作品集被分享浏览次数
     */
    private Long dailyWorksShare;
    /**
     * 当日圈子被点赞数
     */
    private Long dailyCircleGreat;

    /**
     * 当日新增作品数加分
     */
    private Integer scoreNewWorks;
    /**
     * 当日作品集被点赞加分
     */
    private Integer scoreWorkGreat;
    /**
     * 当日作品集被分享加分
     */
    private Integer scoreWorkShare;
    /**
     * 当日圈子被点赞加分
     */
    private Integer scoreCricleGreat;
    /**
     * 当日粉丝增加加分
     */
    private Integer scoreFollow;
}
