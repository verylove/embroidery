package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 19:21
 * @Description:
 */
@ApiModel(value="特价纹身对象",description="特价纹身对象spTattoo")
@Data
public class arUserSpTattooDto {

    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;

    /**
     * 活动名称
     */
    @ApiModelProperty(name = "name",value = "活动名称")
    private String name;
    /**
     * 活动开始日期
     */
    @ApiModelProperty(name = "beginTime",value = "活动开始时间")
    private Date beginTime;
    /**
     * 活动截止日期
     */
    @ApiModelProperty(name = "endTime",value = "活动结束时间")
    private Date endTime;
    /**
     * 原价
     */
    @ApiModelProperty(name = "originalPrice",value = "原价收费")
    private BigDecimal originalPrice;
    /**
     * 优惠价
     */
    @ApiModelProperty(name = "preferentialPrice",value = "特惠价格")
    private BigDecimal preferentialPrice;
    /**
     * 说明
     */
    @ApiModelProperty(name = "description",value = "其他说明")
    private String description;
    /**
     * 微信
     */
    @ApiModelProperty(name = "microLetter",value = "微信",required = false)
    private String microLetter;
    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号",required = false)
    private String phone;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}