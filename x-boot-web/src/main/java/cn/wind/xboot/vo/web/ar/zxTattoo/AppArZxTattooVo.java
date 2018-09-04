package cn.wind.xboot.vo.web.ar.zxTattoo;

import cn.wind.db.ar.entity.ArUserZxPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 10:03
 * @Description:
 */
@Data
public class AppArZxTattooVo {
    private Long id;

    /**
     * 意向图案
     */
    private String intention;
    /**
     * 纹身部位
     */
    private String parts;
    /**
     * 其他说明
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 微信
     */
    private String microLetter;
    /**
     * 电话
     */
    private String phone;
    /**
     * 1-有效 0-失效
     */
    private Integer status;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市
     */
    private Long city;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private List<ArUserZxPic> pics;
}
