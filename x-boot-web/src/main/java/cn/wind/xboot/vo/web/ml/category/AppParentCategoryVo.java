package cn.wind.xboot.vo.web.ml.category;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 22:09
 * @Description:
 */
@Data
public class AppParentCategoryVo {
    private Long id;

    /**
     * 类别名
     */
    private String name;
    /**
     * 图片
     */
    private String coverImg;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
