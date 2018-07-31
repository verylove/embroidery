package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 18:37
 * @Description:
 */
@ApiModel(value="动态作品对象",description="动态作品对象dyWork")
@Data
public class arUserDyWorkDto {

    /**
     * 作品名称
     */
    @ApiModelProperty(name = "name",value = "作品名称",required = false)
    private String name;
    /**
     * 参考价格
     */
    @ApiModelProperty(name = "referencePrice",value = "参考价格",required = false)
    private BigDecimal referencePrice;
    /**
     * 作品耗时
     */
    @ApiModelProperty(name = "timeConsume",value = "作品耗时",required = false)
    private BigDecimal timeConsume;
    /**
     * 纹身故事
     */
    @ApiModelProperty(name = "content",value = "纹身故事")
    private String content;
    /**
     * 1-作品 2-动态  3-动态作品
     */
    @ApiModelProperty(name = "type",value = "1-作品 2-动态  3-动态作品 4-店长发布")
    private Integer type;
    /**
     * 城市ID
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}
