package cn.wind.xboot.vo.web.sr;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 17:22
 * @Description:
 */
@Data
public class AppSrArticleVo {
    private Long id;

    /**
     * 文章名
     */
    private String name;
    /**
     * 说明
     */
    private String describtion;
    /**
     * 补充
     */
    private String remark;
    /**
     * 封面图片
     */
    private String coverImg;
    /**
     * url
     */
    private String url;
    /**
     * 类型 1-纹身素材 2-纹身培训 3-公众号 4-签约纹身店 5-失效
     */
    private Integer type;
}
