package cn.wind.xboot.dto.app.rc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 14:37
 * @Description:
 */
@ApiModel(value="招聘信息对象",description="招聘信息对象recruit")
@Data
public class rcRecruitMentDto {

    /**
     * 城市ID
     */
    @ApiModelProperty(name = "city",value = "城市")
    private Long city;
    /**
     * 性别要求 0-无 1-男 2-女
     */
    @ApiModelProperty(name = "sex",value = "性别要求")
    private Integer sex;
    /**
     * 入职时间
     */
    @ApiModelProperty(name = "inductionTime",value = "入职时间")
    private Date inductionTime;
    /**
     * 入职条件
     */
    @ApiModelProperty(name = "inductionConditions",value = "入职条件")
    private String inductionConditions;
    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;
    /**
     * 发布地点ID
     */
    @ApiModelProperty(name = "cityWhere",value = "发布地点")
    private Long cityWhere;
}
