package cn.wind.xboot.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 20:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushVo {
    private String APP_KEY ="f0941fed47caae32069040c3";
    private String MASTER_SECRET = "8af4bacbeeb84dfdbf221b18";
    private String GROUP_PUSH_KEY = "f0941fed47caae32069040c3";
    private String GROUP_MASTER_SECRET = "8af4bacbeeb84dfdbf221b18";

    private String TITLE = "Test from API example";//通知标题	如果指定了，则通知里原来展示 App名称的地方，将展示成这个字段。
    private String ALERT = "Test from API Example - alert";//通知内容	这里指定了，则会覆盖上级统一指定的 alert 信息；内容可以为空字符串，则表示不展示到通知栏。
    private String MSG_CONTENT = "Test from API Example - msgContent";//	消息内容本身
    private String registration_id = "160a3797c833de8c5b1";//设备标识。一次推送最多 1000 个。
    private String sound;//通知提示声音
    private Integer type;
    private String cid;
    private Boolean isCid;
    private Map<String,String> extra;//附加字段	这里自定义 Key/value 信息，以供业务使用。
    private List<String> REGISTRATION_ID_LIST;
    private List<String> ALIAS_LIST;
    private List<String> TAG_LIST;
}
