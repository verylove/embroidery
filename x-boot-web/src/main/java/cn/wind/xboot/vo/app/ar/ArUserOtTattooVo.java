package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserOtPic;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 17:40
 * @Description:
 */
@Data
public class ArUserOtTattooVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容
     */
    private String content;
    /**
     * 观看人数
     */
    private Integer watchNum;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 点赞数
     */
    private Long greatNum;
    /**
     * 评价数
     */
    private Long messageNum;
    /**
     * 城市
     */
    private Long city;

    private Date createTime;

    private Date modifyTime;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

    private List<ArUserOtPic> pics;
}
