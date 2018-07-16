package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/16 14:13
 * @Description: 图库找图点赞人 分页
 */
@Data
public class ArUserSkGreatNumVo {
    private Long id;

    /**
     * `cx_ar_user_sk_gallery`ID
     */
    private Long seekGalleryId;
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
