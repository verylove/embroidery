package cn.wind.xboot.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class SearchVo implements Serializable {

    private String startDate;

    private String endDate;
}
