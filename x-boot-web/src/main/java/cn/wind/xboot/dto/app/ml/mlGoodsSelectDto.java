package cn.wind.xboot.dto.app.ml;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 15:38
 * @Description:
 */
@ApiModel(value="商品对象",description="商品对象goodsSelect")
@Data
public class mlGoodsSelectDto {

    /**
     * 商品ID
     */
    @ApiModelProperty(name = "goodsId",value = "商品ID",required = true)
    private Long goodsId;
    /**
     * 商品规格
     */
    @ApiModelProperty(name = "specificationsId",value = "商品规格",required = false)
    private Long specificationsId;
    /**
     * 商品型号ID
     */
    @ApiModelProperty(name = "modelsId",value = "商品型号ID",required = false)
    private Long modelsId;
    /**
     * 价格
     */
    @ApiModelProperty(name = "price",value = "该商品总价价格",required = true)
    private BigDecimal price;
    /**
     * 商品数量
     */
    @ApiModelProperty(name = "num",value = "商品数量",required = true)
    private Integer num;

}
