package cn.wind.xboot.vo.app.ar;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 15:40
 * @Description:
 */
@Data
public class ArUserMessageVo {
    private Long id;

    private String title;

    private String content;

    private Long toUserId;

    private String tattooAccount;

    private String tattooContent;

    private Integer category;//1-特价纹身 2-图库找图 3-贴吧话题 4-纹纹达人 5-动态 6-作品

    private Long tattooId;

    private Date createTime;
}
