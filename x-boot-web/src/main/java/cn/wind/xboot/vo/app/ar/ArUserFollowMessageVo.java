package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 17:51
 * @Description: xiaoxi
 */
@Data
public class ArUserFollowMessageVo {
    private String icon;
    private String account;
    private Date createTime;
    private Long userId;
    private Integer isFollowed;//1-关注 0-未关注

}
