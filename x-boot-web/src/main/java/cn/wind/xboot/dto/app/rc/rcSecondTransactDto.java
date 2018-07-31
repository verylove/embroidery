package cn.wind.xboot.dto.app.rc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 15:49
 * @Description:
 */
@ApiModel(value="二手市场对象",description="二手市场对象secondTransact")
@Data
public class rcSecondTransactDto {

    /**
     * 城市ID
     */
    @ApiModelProperty(name = "city",value = "城市")
    private Long city;
    /**
     * 产品名称
     */
    @ApiModelProperty(name = "productName",value = "产品名称")
    private String productName;
    /**
     * 购买时间
     */
    @ApiModelProperty(name = "buyTime",value = "购买时间")
    private Date buyTime;
    /**
     * 购买价格
     */
    @ApiModelProperty(name = "buyPrice",value = "购买价格")
    private BigDecimal buyPrice;
    /**
     * 转让价格
     */
    @ApiModelProperty(name = "transferPrice",value = "转让价格")
    private BigDecimal transferPrice;
    /**
     * 其他说明
     */
    @ApiModelProperty(name = "content",value = "其他说明")
    private String content;
    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;
    /**
     * 发布城市ID
     */
    @ApiModelProperty(name = "cityWhere",value = "发布城市ID")
    private Long cityWhere;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}
