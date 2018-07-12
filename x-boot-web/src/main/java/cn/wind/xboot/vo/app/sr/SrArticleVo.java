package cn.wind.xboot.vo.app.sr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 11:32
 * @Description:
 */
@Data
@ApiModel(value="文章资讯",description="文章对象article")
public class SrArticleVo {
    private Long id;
    @ApiModelProperty(name = "name",value = "文章名")
    private String name;
    @ApiModelProperty(name = "coverImg",value = "封面图片")
    private String coverImg;
    @ApiModelProperty(name = "url",value = "文章链接")
    private String url;
    @ApiModelProperty(name = "type",value = "文章类型")
    private Integer type;
}
