package cn.wind.xboot.vo.app.ar;

import cn.wind.db.ar.entity.ArUserSkPic;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/13 16:45
 * @Description:
 */
@Data
public class ArUserSkGalleryVo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 内容描述
     */
    private String content;
    /**
     * 封面
     */
    private String coverImg;
    /**
     * 观看次数
     */
    private Integer watchNum=0;
    /**
     * 发布位置
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 留言数
     */
    private Long messageNum=0L;

    private String aduit;//认证标签
    private String businessCard;//名片
    private Integer perLevel;//等级
    private String icon;//头像
    private String account;
    private Integer isCollection;//1-关注 0-不关注
    private Integer isGreat;//1-点赞 0-未点赞

    private List<ArUserSkPic> pics;
}
