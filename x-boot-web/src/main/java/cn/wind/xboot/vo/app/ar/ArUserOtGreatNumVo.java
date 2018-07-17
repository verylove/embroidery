package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 19:47
 * @Description:
 */
@Data
public class ArUserOtGreatNumVo {
    private Long id;

    /**
     * `cx_ar_user_ot_tattoo`ID
     */
    private Long otTattooId;
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
