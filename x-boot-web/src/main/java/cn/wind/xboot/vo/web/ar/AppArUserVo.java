package cn.wind.xboot.vo.web.ar;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/16 20:35
 * @Description:
 */
@Data
public class AppArUserVo {
    private Long id;
    private String account;
    private String phone;
    private String icon;
    private Integer sex;
    private String profile;
    private Integer identity;
    private Long province;
    private Long city;
    private Long county;
    private String perAddress;
    private Integer workNum;
    private String workPlace;
    private Integer signStatus;
    private Integer nameStatus;
    private Integer storeStatus;
    private Integer perLevel;
    private Long activeNum;
    private Long charmNum;
    private Long wealthNum;
    private BigDecimal balance;
    private Long sentimentNum;
    private Long praiseNum;
    private Long focusNum;
    private Long followNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
