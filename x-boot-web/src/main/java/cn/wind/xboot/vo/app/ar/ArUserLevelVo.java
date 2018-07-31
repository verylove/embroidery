package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/24 10:04
 * @Description:
 */
@Data
public class ArUserLevelVo {

    /**
     * 个人等级
     */
    private Integer perLevel;
    /**
     * 活跃值
     */
    private Long activeNum;
    /**
     * 魅力值
     */
    private Long charmNum;
    /**
     * 财富值
     */
    private Long wealthNum;
    /**
     * 昨天加分
     */
    private Long ydPlusNum;
//    /**
//     * 当前分值
//     */
//    private Long nowAllNum;
    /**
     * 当前等级值要求
     */
    private Integer nowLevelNum;
    /**
     * 下一层等级值要求
     */
    private Integer nextLevelNum;

}
