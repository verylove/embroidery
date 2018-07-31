package cn.wind.db.ar.entity;

import java.time.LocalDate;
import com.baomidou.mybatisplus.annotations.TableName;
import cn.wind.mybatis.common.AuditEntity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户每日新增数据记录数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_daily_record")
public class ArUserDailyRecord extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 当日新增活跃值
     */
    private Long dailyActiveNum=0L;
    /**
     * 当日新增魅力值
     */
    private Long dailyCharmNum=0L;
    /**
     * 当日新增财富值
     */
    private Long dailyWealthNum=0L;
    /**
     * 当日点赞次数
     */
    private Long dailyGreatNum=0L;
    /**
     * 当日被赞次数
     */
    private Long dailyPraiseNum=0L;
    /**
     * 当日的人气
     */
    private Long dailySentiment=0L;
    /**
     * 当日关注
     */
    private Long dailyFocus=0L;
    /**
     * 当日被关注(粉丝)
     */
    private Long dailyFollow=0L;
    /**
     * 当日新增作品集数
     */
    private Long dailyNewWorks=0L;
    /**
     * 当日作品集被点赞次数
     */
    private Long dailyWorksGreat=0L;
    /**
     * 当日作品集被分享浏览次数
     */
    private Long dailyWorksShare=0L;
    /**
     * 当日圈子被点赞数
     */
    private Long dailyCircleGreat=0L;
    /**
     * 当天日期
     */
    private LocalDate nowDate;
    /**
     * 当日新增作品数加分
     */
    private Integer scoreNewWorks=0;
    /**
     * 当日作品集被点赞加分
     */
    private Integer scoreWorkGreat=0;
    /**
     * 当日作品集被分享加分
     */
    private Integer scoreWorkShare=0;
    /**
     * 当日圈子被点赞加分
     */
    private Integer scoreCricleGreat=0;
    /**
     * 当日粉丝增加加分
     */
    private Integer scoreFollow=0;


}
