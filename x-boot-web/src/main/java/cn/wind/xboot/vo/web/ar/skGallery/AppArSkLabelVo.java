package cn.wind.xboot.vo.web.ar.skGallery;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/4 15:03
 * @Description:
 */
@Data
public class AppArSkLabelVo {
    private Long id;

    /**
     * label名
     */
    private String name;
    /**
     * 等级 1-父级 2-子级
     */
    private Integer level;
    /**
     * 所属层级 level为1时为0
     */
    private Long pid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String parentName;
}
