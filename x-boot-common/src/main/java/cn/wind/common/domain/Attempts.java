package cn.wind.common.domain;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author xukk
 * @date 2018/5/4
 */
@Data
public class Attempts {
    private Long id;
    private String username;
    private Integer attempts;
    private Date lastModified;
}
