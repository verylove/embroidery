package cn.wind.xboot.vo.app.ar;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 09:58
 * @Description:
 */
@Data
public class ArUserStoreVo {
    private Long id;

    private Long userId;

    private String Icon;

    private String storeName;

    private String storeAddress;

    private String Audit;

    private Long sentimentNum;

    private Long followNum;
}
