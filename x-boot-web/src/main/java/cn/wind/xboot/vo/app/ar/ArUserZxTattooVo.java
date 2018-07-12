package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserZxPic;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 19:47
 * @Description:
 */
@Data
public class ArUserZxTattooVo {

    private Long id;

    /**
     * 意向图案
     */
    private String intention;
    /**
     * 纹身部位
     */
    private String parts;
    /**
     * 其他说明
     */
    private String description;
    /**
     * 微信
     */
    private Integer isMicroLetter;
    /**
     * 电话
     */
    private Integer isPhone;
    /**
     * 用户ID
     */
    private Long userId;
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

    private List<ArUserZxPic> pics;
}
