package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 19:19
 * @Description:
 */
@Data
public class ArAppointOrderVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 纹身师ID
     */
    private Long artistId;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 需求描述
     */
    private String content;
    /**
     * 预约日期
     */
    private Date appointDate;
    /**
     * 预约时间
     */
    private String appointTime;

    private Integer status;

    private Date createTime;

    private Date modifyTime;

    private String businessCard;//名片 全是纹身爱好者
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;


}
