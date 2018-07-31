package cn.wind.xboot.vo.app.ml;

import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 19:47
 * @Description:
 */
@Data
public class MlUserAddressVo {
    private Long id;

    /**
     * 收件人
     */
    private String name;
    /**
     * 省ID
     */
    private Long province;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 县区ID
     */
    private Long county;
    /**
     * 地址详情
     */
    private String adressDetail;
    /**
     * 收件人手机号
     */
    private String phone;
    /**
     * 1-默认 0-不默认
     */
    private Integer type;
}
