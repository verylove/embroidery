package cn.wind.xboot.vo.web.rc;

import cn.wind.db.rc.entity.RcSecondPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/5 17:13
 * @Description:
 */
@Data
public class AppRcSecondTransactVo {
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
    private BigDecimal buyPrice;
    /**
     * 转让价格
     */
    private BigDecimal transferPrice;
    /**
     * 其他说明
     */
    private String content;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 发布城市ID
     */
    private Long cityWhere;
    /**
     * 1-有效 0-失效
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private List<RcSecondPic> pics;
}
