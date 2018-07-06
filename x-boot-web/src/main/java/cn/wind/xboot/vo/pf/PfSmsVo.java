package cn.wind.xboot.vo.pf;

import cn.wind.db.pf.entity.PfSms;
import lombok.Data;

/**
 * <p>Title: PfSmsVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
@Data
public class PfSmsVo {
    private Long id;
    private String title;
    private String templateCode;
    private String content;
    private Integer delFlag;
}
