package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserTbPic;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 09:36
 * @Description:
 */
@Data
public class ArUserTbTopicVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 留言数
     */
    private Long messageNum=0L;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 1-贴吧 2-话题
     */
    private Integer type;

    private Date createTime;

    private Date modifyTime;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

    private List<ArUserTbPic> pics;

}
