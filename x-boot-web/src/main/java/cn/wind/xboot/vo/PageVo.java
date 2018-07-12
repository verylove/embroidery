package cn.wind.xboot.vo;

import cn.wind.common.utils.Underline2CamelUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Xukk
 */
@Data
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页号")
    private int pageNumber;

    @ApiModelProperty(value = "页面大小")
    private int pageSize;

    @ApiModelProperty(value = "排序字段")
    private List<String>  sort;

    public Page<T> initPage() {
        Page<T> page = new Page(this.getPageNumber(), this.getPageSize());
        if (this.sort != null && this.sort.size() > 0) {
            List<String> sortNew=Lists.newArrayList();
            this.sort.forEach(v-> Arrays.stream(v.split(",")).forEach(t-> sortNew.add(t)));
             //Arrays.stream(v.split(",")).filter(t->t.equalsIgnoreCase("ASC")||t.equalsIgnoreCase("DESC")).findAny().ifPresent(k->atomicInteger.incrementAndGet()));
            int begin = 0;
            boolean flag = false;
            for (int i = 0; i < sortNew.size(); i++) {
                if (i != 0) {
                    if (StringUtils.equalsIgnoreCase(sortNew.get(i), "ASC")) {
                        flag = true;
                        page.setAscs(sortNew.subList(begin, i).stream().map(v-> Underline2CamelUtil.camel2Underline(v,true)).collect(Collectors.toList()));
                        if ((i + 1) < sortNew.size()) {
                            begin = i + 1;
                        }
                    } else if (StringUtils.equalsIgnoreCase(sortNew.get(i), "DESC")) {
                        flag = true;
                        page.setDescs(sortNew.subList(begin, i).stream().map(v-> Underline2CamelUtil.camel2Underline(v,true)).collect(Collectors.toList()));
                        if ((i + 1) < sortNew.size()) {
                            begin = i + 1;
                        }
                    }
                }
            }
            if(!flag){
                page.setAscs(sortNew.stream().map(v-> Underline2CamelUtil.camel2Underline(v,true)).collect(Collectors.toList()));
            }
        }
        return page;
    }
}
