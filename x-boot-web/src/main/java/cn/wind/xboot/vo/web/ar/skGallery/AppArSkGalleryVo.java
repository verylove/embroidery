package cn.wind.xboot.vo.web.ar.skGallery;

import cn.wind.db.ar.entity.ArUserSkPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 11:50
 * @Description:
 */
@Data
public class AppArSkGalleryVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容描述
     */
    private String content;
    /**
     * 标签
     */
    private Long label;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 观看次数
     */
    private Integer watchNum;
    /**
     * 发布位置
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 留言数
     */
    private Long messageNum;
    /**
     * 1-有效 0-失效
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private String labelName;

    private List<ArUserSkPic> pics;
}


