package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserSpPic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 17:20
 * @Description:
 */
@Data
public class ArUserSpTattooVo {
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
     * 点赞数
     */
    private Long greatNum;
    /**
     * 留言数
     */
    private Long messageNum;

    private Date createTime;

    private Date modifyTime;

    private Long distance;//活动地与自己的距离

    private Long user_id;
    private String aduit;//认证标签
    private Integer perLevel;
    private String icon;//头像
    private String account;
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

    private List<ArUserSpPic> pics;

}
