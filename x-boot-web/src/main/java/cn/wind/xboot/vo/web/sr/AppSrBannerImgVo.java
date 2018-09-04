package cn.wind.xboot.vo.web.sr;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 14:12
 * @Description:
 */
@Data
public class AppSrBannerImgVo {
    private Long id;
    /**
     * 轮播图
     */
    private String img;
    /**
     * 轮播图位置 1-首页 2-商城
     */
    private Integer category;
    /**
     * 轮播图类型 1-URL
     */
    private Integer type;
    /**
     * 轮播图key值
     */
    private String imgKey;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
