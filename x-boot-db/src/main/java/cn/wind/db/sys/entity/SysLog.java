package cn.wind.db.sys.entity;

import cn.hutool.json.JSONUtil;
import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author xukk
 * @since 2018-06-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_log")
public class SysLog extends AuditEntity {

    private static final long serialVersionUID = 1L;

    @TableLogic
    private Integer delFlag;
    private Integer costTime;
    private String ip;
    private String ipInfo;
    private String name;
    private String requestParam;
    private String requestType;
    private String requestUrl;
    private String username;
    /**
     * 转换请求参数为Json
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {

        this.requestParam = JSONUtil.toJsonStr(paramMap);
    }

}
