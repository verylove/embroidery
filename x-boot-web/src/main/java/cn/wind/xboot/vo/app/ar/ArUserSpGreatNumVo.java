package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 09:41
 * @Description:
 */
@Data
public class ArUserSpGreatNumVo {
    private Long id;
    /**
     * `cx_ar_user_sp_tattoo`ID
     */
    private Long specialTattooId;
    /**
     * `cx_ar_user`ID 点赞人
     */
    private Long userId;
    /**
     * `cx_ar_user`ID 作者
     */
    private Long authorId;
    /**
     * 点赞数
     */
    private Integer num=0;
    /**
     * 用户名
     */
    private String account;
    /**
     * 用户头像
     */
    private String icon;
}
