package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 16:01
 * @Description:
 */
@ApiModel(value="店铺认证对象",description="店铺认证对象storeAduit")
@Data
public class arStoreAduitDto {

    /**
     * 店铺名称
     */
    @ApiModelProperty(name = "storeName",value = "店铺名")
    private String storeName;
    /**
     * 详细地址
     */
    @ApiModelProperty(name = "storeAddress",value = "店铺地址")
    private String storeAddress;
    /**
     * 营业执照
     */
    @ApiModelProperty(name = "busLicense",value = "营业执照")
    private String busLicense;

    @ApiModelProperty(name = "pics",value = "店内环境")
    private List<String> pics;
}
