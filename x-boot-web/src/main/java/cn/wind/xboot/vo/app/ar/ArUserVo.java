package cn.wind.xboot.vo.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 17:03
 * @Description:
 */
@Data
public class ArUserVo {
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
    private String payPass;
    private Long liveEarnings;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

}
