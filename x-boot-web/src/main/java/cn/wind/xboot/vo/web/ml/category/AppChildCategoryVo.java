package cn.wind.xboot.vo.web.ml.category;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 22:26
 * @Description:
 */
@Data
public class AppChildCategoryVo {
    private Long id;

    /**
     * 类别名
     */
    private String name;
    /**
     * 上一级ID 父级为0
     */
    private Long pid;
    /**
     * 图片
     */
    private String coverImg;

    private String parentName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
