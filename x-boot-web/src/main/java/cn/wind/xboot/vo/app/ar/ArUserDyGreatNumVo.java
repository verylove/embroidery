package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/19 15:04
 * @Description:
 */
@Data
public class ArUserDyGreatNumVo {
    private Long id;
    /**
     * `cx_ar_user_dy_works`ID
     */
    private Long dyWorksId;
    /**
     * `cx_ar_user`ID 点赞人ID
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 作者ID
     */
    private Long authorId;
    /**
     * 点赞次数
     */
    private Integer num;

    private String account;

    private String icon;
}
