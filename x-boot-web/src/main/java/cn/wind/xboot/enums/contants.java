package cn.wind.xboot.enums;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/13 16:33
 * @Description:
 */
public class contants implements java.io.Serializable{

    public static final String defaultHeadImg = "https://apollobucket.oss-cn-beijing.aliyuncs.com/head_img.png";//默认头像
    public static final String defaultCoverImg = "https://apollobucket.oss-cn-beijing.aliyuncs.com/default.jpeg";//默认封面
    public static final String defaultPassword = "a111111";

    /**
     * 以下 人气
     */
    public static final Integer workNew = 1;//新增作品
    public static final Integer workGreat = 1;//作品被点赞
    public static final Integer workShare = 1;//作品被分享
    public static final Integer circleGreat = 1;//圈子被点赞
    public static final Integer follows = 1;//被关注 粉丝

    public static final Long workNewLong = 1L;
    public static final Long workGreatLong = 1L;
    public static final Long workShareLong = 1L;
    public static final Long circleGreatLong = 1L;
    public static final Long followsLong = 1L;




    /**
     * 以下 level
     */
    public static final Integer publishDyNum = 2;
    public static final Integer followLevel = 1;
    public static final Integer charmGreat = 1;//点赞产生的魅力值
}
