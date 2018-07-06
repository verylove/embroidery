package cn.wind.common.domain;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Title: Statistics</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
@Data
@Builder
public class Statistics {
    private Integer total;
    private AtomicInteger avi;
}
