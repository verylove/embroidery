package cn.wind.xboot.dto.app.ar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/23 19:36
 * @Description:
 */
@ApiModel(value="银行卡对象",description="银行卡对象bankDto")
@Data
public class arUserBankDto {
    @ApiModelProperty(name = "name",value = "真实姓名")
    private String name;
    /**
     * 卡号
     */
    @ApiModelProperty(name = "bankCard",value = "卡号")
    private String bankCard;
    /**
     * 身份证
     */
    @ApiModelProperty(name = "identityCard",value = "身份证")
    private String identityCard;
    /**
     * 所属银行
     */
    @ApiModelProperty(name = "bankName",value = "所属银行")
    private String bankName;
    /**
     * 支行信息
     */
    @ApiModelProperty(name = "branchName",value = "支行信息")
    private String branchName;

    @ApiModelProperty(name = "phone",value = "电话")
    private String phone;

    @ApiModelProperty(name = "code",value = "验证码")
    private String code;
}
