package cn.wind.xboot.message.push;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author xukk
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PushDto {
    private PushDeviceTypeEnum  pushDeviceType= PushDeviceTypeEnum.ANDROID;
    PushTypeEnum pushType;
    /**
     * ，通知栏提示文字
     */
    @NotNull
    String ticker;
    /**
     * 通知标题
     */
    @NotNull
    String title;
    /**
     * 通知文字描述
     */
    @NotNull
    String text;
    PushAfterOpenEnum after_open= PushAfterOpenEnum.go_app;
    PushDisplayTypeEnum display_type= PushDisplayTypeEnum.notification;
    boolean production_mode=true;
    Map<String,String> extraMap;
    private String device_tokens;
    private String filterJson;
    private String alias_type;
    private String file_id;
    private Integer badge;
    private String sound;
    private Integer content_available;
    private String category;
    private String alias;
    public static enum PushTypeEnum {
        //单播
        unicast,
        //列播
        listcast,
        //广播
        broadcast,
        //组播
        groupcast,
        //文件播
        filecast,
        //自定义播
        Customizedcast
    }
    public static enum PushDisplayTypeEnum {
        notification,message
    }
    public static  enum  PushAfterOpenEnum {
        go_app,go_url,go_activity,go_Custom
    }
    public static enum PushDeviceTypeEnum {
        ANDROID,IOS
    }

}
