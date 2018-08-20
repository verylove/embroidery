package cn.wind.xboot.vo.web.ar;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/17 15:13
 * @Description:
 */
@Data
public class AppArTattooAduitVo {

    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String cardNum;
    /**
     * 身份证照正面
     */
    private String cardPicOne;
    /**
     * 身份证照反面
     */
    private String cardPicTwo;
    /**
     * 身份证照手持
     */
    private String cardPicThree;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 状态 1-待审批 2-审批通过 3-审批拒绝
     */
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private AppArUserVo user;

    private String phone;
}
