package cn.wind.xboot.vo.web.ar.spTattoo;

import cn.wind.db.ar.entity.ArUserSpEvaluates;
import cn.wind.db.ar.entity.ArUserSpPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 15:31
 * @Description:
 */
@Data
public class AppArSpTattooVo {
    private Long id;

    /**
     * 所在城市
     */
    private Long city;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动开始日期
     */
    private Date beginTime;
    /**
     * 活动截止日期
     */
    private Date endTime;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 优惠价
     */
    private BigDecimal preferentialPrice;
    /**
     * 说明
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 微信
     */
    private String microLetter;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 类型 1-有效 0-失效
     */
    private Integer status;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 留言数
     */
    private Long messageNum;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private List<ArUserSpPic> pics;
}
