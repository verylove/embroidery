package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/16 20:12
 * @Description:贴吧话题
 */
@ApiModel(value="贴吧话题对象",description="贴吧话题对象tbTopic")
@Data
public class arUserTbTopicDto {
    /**
     * 标题
     */
    @ApiModelProperty(name = "title",value = "标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(name = "content",value = "内容")
    private String content;
    /**
     * 城市ID
     */
    @ApiModelProperty(name = "city",value = "城市ID")
    private Long city;
    /**
     * 1-贴吧 2-话题
     */
    private Integer type;

    @ApiModelProperty(name = "pics",value = "图片")
    private List<String> pics;
}
