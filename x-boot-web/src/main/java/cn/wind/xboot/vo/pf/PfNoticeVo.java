package cn.wind.xboot.vo.pf;

import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Data;

/**
 * <p>Title: PfNoticeVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/7/2
 */
@Data
public class PfNoticeVo {

    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     *  轮播排序（可用于置顶）
     */
    private Integer sortOrder;
    /**
     * 是否已读
     */
    private Integer readed;
    private Integer delFlag;

    private Long time;
}
