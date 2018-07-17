package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 16:40
 * @Description:
 */
@ApiModel(value="纹纹达人对象",description="纹纹达人对象otTattoo")
@Data
public class arUserOtTattooDto {

    /**
     * 内容
     */
    @ApiModelProperty(name = "content",value = "内容")
    private String content;
    /**
     * 城市
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;

}
