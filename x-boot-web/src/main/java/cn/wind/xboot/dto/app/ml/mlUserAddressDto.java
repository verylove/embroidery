package cn.wind.xboot.dto.app.ml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/31 15:03
 * @Description:
 */
@ApiModel(value="收件地址对象",description="收件地址userAddress")
@Data
public class mlUserAddressDto {

    /**
     * 收件人
     */
    @ApiModelProperty(name = "name",value = "收件人")
    private String name;
    /**
     * 省ID
     */
    @ApiModelProperty(name = "province",value = "省ID")
    private Long province;
    /**
     * 城市ID
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;
    /**
     * 县区ID
     */
    @ApiModelProperty(name = "county",value = "县区ID")
    private Long county;
    /**
     * 地址详情
     */
    @ApiModelProperty(name = "adressDetail",value = "地址详情")
    private String adressDetail;
    /**
     * 收件人手机号
     */
    @ApiModelProperty(name = "phone",value = "收件人手机号")
    private String phone;
    /**
     * 1-默认 0-不默认
     */
    @ApiModelProperty(name = "type",value = " 1-默认 0-不默认")
    private Integer type;
}
