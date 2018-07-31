package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 17:22
 * @Description:
 */
@ApiModel(value="预约订单对象",description="预约订单对象appointOrder")
@Data
public class arAppointOrderDto {

    /**
     * 纹身师ID
     */
    @ApiModelProperty(name = "artistId",value = "纹身师ID")
    private Long artistId;
    /**
     * 昵称
     */
    @ApiModelProperty(name = "nick",value = "昵称")
    private String nick;
    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;
    /**
     * 需求描述
     */
    @ApiModelProperty(name = "content",value = "需求描述")
    private String content;
    /**
     * 预约日期
     */
    @ApiModelProperty(name = "appointDate",value = "预约日期")
    private Date appointDate;
    /**
     * 预约时间
     */
    @ApiModelProperty(name = "appointTime",value = "预约时间")
    private String appointTime;
}
