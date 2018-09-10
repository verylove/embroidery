package cn.wind.xboot.vo.web.ar.tbTopic;

import cn.wind.db.ar.entity.ArUserTbPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 17:24
 * @Description:
 */
@Data
public class AppArTbTopicVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 留言数
     */
    private Long messageNum;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 1-贴吧 2-话题
     */
    private Integer type;
    /**
     * 1-有效 0-失效
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private List<ArUserTbPic> pics;
}
