package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 19:27
 * @Description:
 */
@ApiModel(value="纹身咨询对象",description="纹身咨询对象spTattoo")
@Data
public class arUserZxTattooDto {

    /**
     * 意向图案
     */
    @ApiModelProperty(name = "intention",value = "意向图案")
    private String intention;
    /**
     * 纹身部位
     */
    @ApiModelProperty(name = "parts",value = "纹身部位")
    private String parts;
    /**
     * 其他说明
     */
    @ApiModelProperty(name = "description",value = "补充描述")
    private String description;
    /**
     * 微信
     */
    @ApiModelProperty(name = "microLetter",value = "微信")
    private String microLetter;
    /**
     * 电话 1-接受 0-不接受
     */
    @ApiModelProperty(name = "isPhone",value = "是否接受电话沟通")
    private Integer isPhone;
    /**
     * 城市
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}
