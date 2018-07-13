package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/13 15:07
 * @Description:
 */
@ApiModel(value="图库找图对象",description="图库找图对象skGallery")
@Data
public class arUserSkGalleryDto {

    /**
     * 内容描述
     */
    @ApiModelProperty(name = "content",value = "内容描述")
    private String content;
    /**
     * 标签
     */
    @ApiModelProperty(name = "label",value = "标签ID")
    private Long label;
    /**
     * 发布位置
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}
