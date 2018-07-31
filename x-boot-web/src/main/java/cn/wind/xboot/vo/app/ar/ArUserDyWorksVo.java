package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserDyPic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/19 10:25
 * @Description:
 */
@Data
public class ArUserDyWorksVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作品名称
     */
    private String name;
    /**
     * 参考价格
     */
    private BigDecimal referencePrice;
    /**
     * 作品耗时
     */
    private BigDecimal timeConsume;
    /**
     * 纹身故事
     */
    private String content;
    /**
     * 1-作品 2-动态  3-动态作品
     */
    private Integer type;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 评价数
     */
    private Long messageNum;

    private Date createTime;

    private Date modifyTime;

    private Long distance;//活动地与自己的距离

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

    /**
     * 店铺用
     */
    private Long county;//某某区
    private String perAddress;//详细地址
    private Long storeCity;//店铺地址

    private List<ArUserDyPic> pics;
}
