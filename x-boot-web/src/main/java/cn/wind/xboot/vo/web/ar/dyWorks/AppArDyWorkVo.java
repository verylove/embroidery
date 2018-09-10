package cn.wind.xboot.vo.web.ar.dyWorks;

import cn.wind.db.ar.entity.ArUserDyPic;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 18:37
 * @Description:
 */
@Data
public class AppArDyWorkVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作品名称
     */
    private String name;
    /**
     * 参考价格
     */
    private BigDecimal referencePrice;
    /**
     * 作品耗时
     */
    private BigDecimal timeConsume;
    /**
     * 纹身故事
     */
    private String content;
    /**
     * 1-作品 2-动态  3-动态作品
     */
    private Integer type;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 评价数
     */
    private Long messageNum;
    /**
     * 1-有效 0-失效
     */
    private Integer status;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;

    private List<ArUserDyPic> pics;
}
