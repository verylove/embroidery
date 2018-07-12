package cn.wind.xboot.vo.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 17:03
 * @Description:
 */
@Data
public class ArUserVo {
    @ApiModelProperty(name = "id",value = "主键",dataType = "Long")
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
}
