package cn.wind.xboot.vo.web.ar;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/17 16:51
 * @Description:
 */
@Data
public class AppArStoreAduitVo {

    private Long id;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 详细地址
     */
    private String storeAddress;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 描述
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 申请内容
     */
    private String content;
    /**
     * 状态 1待审批 2-审批通过 3-审批拒绝
     */
    private Integer status;
    /**
     * 营业执照
     */
    private String busLicense;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private AppArUserVo userVo;

    private String account;

    private String phone;

}
