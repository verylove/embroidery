package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 16:19
 * @Description:
 */
@ApiModel(value="实名认证对象",description="实名认证对象nameAduit")
@Data
public class arNameAduitDto {

    /**
     * 姓名
     */
    @ApiModelProperty(name = "name",value = "用户名")
    private String name;
    /**
     * 身份证号
     */
    @ApiModelProperty(name = "cardNum",value = "身份证号")
    private String cardNum;
    /**
     * 身份证照正面
     */
    @ApiModelProperty(name = "cardPicOne",value = "身份证照正面")
    private String cardPicOne;
    /**
     * 身份证照反面
     */
    @ApiModelProperty(name = "cardPicTwo",value = "身份证照反面")
    private String cardPicTwo;
    /**
     * 身份证照手持
     */
    @ApiModelProperty(name = "cardPicThree",value = "身份证照手持")
    private String cardPicThree;
}
