package cn.wind.xboot.vo.web.rc;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/5 16:51
 * @Description:
 */
@Data
public class AppRcRecruitmentVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 性别要求 0-无 1-男 2-女
     */
    private Integer sex;
    /**
     * 入职时间
     */
    private Date inductionTime;
    /**
     * 入职条件
     */
    private String inductionConditions;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 发布地点ID
     */
    private Long cityWhere;
    /**
     * 1-有效 0-失效
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String account;

    private String cityName;
}
