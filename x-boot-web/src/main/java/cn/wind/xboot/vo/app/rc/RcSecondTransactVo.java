package cn.wind.xboot.vo.app.rc;

import cn.wind.db.rc.entity.RcSecondPic;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 16:19
 * @Description:
 */
@Data
public class RcSecondTransactVo {
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
     * 产品名称
     */
    private String productName;
    /**
     * 购买时间
     */
    private Date buyTime;
    /**
     * 购买价格
     */
    private BigDecimal buyPrice=new BigDecimal("0.00");
    /**
     * 转让价格
     */
    private BigDecimal transferPrice=new BigDecimal("0.00");
    /**
     * 其他说明
     */
    private String content;
    /**
     * 发布城市ID
     */
    private Long cityWhere;

    private Date createTime;

    private Date modifyTime;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;

    private List<RcSecondPic> pics;
}
