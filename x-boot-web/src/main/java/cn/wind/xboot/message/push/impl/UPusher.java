package cn.wind.xboot.message.push.impl;

import cn.wind.common.res.ApiRes;
import cn.wind.xboot.message.push.PushConfig;
import cn.wind.xboot.message.push.PushDto;
import cn.wind.xboot.message.push.Pusher;
import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import push.AndroidNotification;
import push.IOSNotification;
import push.UmengNotification;
import push.android.*;
import push.ios.*;

/**
 *  "根据参数进行推送\n"+
 " * \"ticker\":\"xx\",     // 必填，通知栏提示文字\n" +
 " * \"title\":\"xx\",      // 必填，通知标题\n" +
 " * \"text\":\"xx\",       // 必填，通知文字描述\n" +
 " * 点击\"通知\"的后续行为，默认为打开app。\n" +
 " * \"after_open\": \"xx\" // 必填，值可以为:\n" +
 " *      \"go_app\": 打开应用\n" +
 " *      \"go_url\": 跳转到URL\n" +
 " *      \"go_activity\": 打开特定的activity\n" +
 " *      \"go_Custom\": 用户自定义内容。\n" +
 " * \"display_type\":\"xx\",   // 必填，消息类型: notification(通知)、message(消息)\n" +
 " * \"production_mode\":\"true/false\", // 可选，正式/测试模式。默认为true\n" +
 " * 测试模式只会将消息发给测试设备。测试设备需要到web上添加。\n" +
 " * Android: 测试设备属于正式设备的一个子集。\n" +
 " * \"device_tokens\":\"xx\", // 当type=unicast时, 必填, 表示指定的单个设备\n" +
 " * 当type=listcast时, 必填, 要求不超过500个, 以英文逗号分隔"
 * 友盟推送
 * @author xukk
 * @date 2018/5/23
 */
public class UPusher implements Pusher {
    private PushConfig.PushConfigDto pushConfigDto;

    public UPusher(PushConfig.PushConfigDto pushConfigDto) {
        this.pushConfigDto = pushConfigDto;
    }

    @Override
    public ApiRes push(PushDto pushDto) {
        switch (pushDto.getPushType()) {
            case unicast:
            case listcast:
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    AndroidUnicast androidUnicast = new AndroidUnicast();
                    return assembly(pushConfigDto, androidUnicast, pushDto);
                } else {
                    IOSUnicast iosUnicast = new IOSUnicast();
                    return assembly(pushConfigDto, iosUnicast, pushDto);
                }
            case groupcast:
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    AndroidGroupcast androidGroupcast = new AndroidGroupcast();
                    return assembly(pushConfigDto, androidGroupcast, pushDto);
                } else {
                    IOSGroupcast iosGroupcast = new IOSGroupcast();
                    return assembly(pushConfigDto, iosGroupcast, pushDto);
                }
            case filecast:
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    AndroidFilecast androidFilecast = new AndroidFilecast();
                    return assembly(pushConfigDto, androidFilecast, pushDto);
                } else {
                    IOSFilecast iosFilecast = new IOSFilecast();
                    return assembly(pushConfigDto, iosFilecast, pushDto);
                }
            case Customizedcast:
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    AndroidCustomizedcast androidCustomizedcast = new AndroidCustomizedcast();
                    return assembly(pushConfigDto, androidCustomizedcast, pushDto);
                } else {
                    IOSCustomizedcast iosCustomizedcast = new IOSCustomizedcast();
                    return assembly(pushConfigDto, iosCustomizedcast, pushDto);
                }
            case broadcast:
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    AndroidBroadcast androidBroadcast = new AndroidBroadcast();
                    return assembly(pushConfigDto, androidBroadcast, pushDto);
                } else {
                    IOSBroadcast iosBroadcast = new IOSBroadcast();
                    return assembly(pushConfigDto, iosBroadcast, pushDto);
                }
            default: return ApiRes.Custom().failure();
        }
    }
     @SneakyThrows
     ApiRes assembly(PushConfig.PushConfigDto pushConfigDto, UmengNotification umengNotification, PushDto pushDto) {
        umengNotification.setAppMasterSecret(pushConfigDto.getAppMasterSecret());
        umengNotification.setPredefinedKeyValue("appkey", pushConfigDto.getAppKey());
        umengNotification.setPredefinedKeyValue("timestamp", pushConfigDto.getTimestamp());
        umengNotification.setPredefinedKeyValue("ticker", pushDto.getTicker());
        umengNotification.setPredefinedKeyValue("title", pushDto.getTitle());
        umengNotification.setPredefinedKeyValue("text", pushDto.getText());
        umengNotification.setPredefinedKeyValue("after_open", pushDto.getAfter_open().name());
        umengNotification.setPredefinedKeyValue("display_type", pushDto.getDisplay_type().name());
        umengNotification.setPredefinedKeyValue("production_mode", pushDto.isProduction_mode());
        if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.IOS)) {
            if (pushDto.getBadge() != null) {
                umengNotification.setPredefinedKeyValue("badge", pushDto.getBadge());
            }
            if (pushDto.getSound() != null) {
                umengNotification.setPredefinedKeyValue("sound", pushDto.getSound());
            }
            if (pushDto.getContent_available() != null) {
                umengNotification.setPredefinedKeyValue("content-available", pushDto.getContent_available());
            }
            if (pushDto.getCategory() != null) {
                umengNotification.setPredefinedKeyValue("category", pushDto.getCategory());
            }
        }
        if (pushDto.getExtraMap() != null && pushDto.getExtraMap().size() > 0) {
            pushDto.getExtraMap().forEach((k, v) -> {
                try {
                    if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                        ((AndroidNotification) umengNotification).setExtraField(k, v);
                    } else {
                        ((IOSNotification) umengNotification).setCustomizedField(k, v);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        switch (pushDto.getPushType()) {
            case unicast:
            case listcast:
                Preconditions.checkNotNull(pushDto.getDevice_tokens(), "type=listcast或unicast时,device_tokens必填");
                umengNotification.setPredefinedKeyValue("device_tokens", pushDto.getDevice_tokens());
                break;
            case filecast:
                Preconditions.checkNotNull(pushDto.getFile_id(), "type=filecast时,file_id必填");
                if (pushDto.getPushDeviceType().equals(PushDto.PushDeviceTypeEnum.ANDROID)) {
                    ((AndroidFilecast) umengNotification).uploadContents(pushDto.getFile_id());
                } else {
                    ((IOSFilecast) umengNotification).uploadContents(pushDto.getFile_id());
                }
                break;
            case groupcast:
                Preconditions.checkNotNull(pushDto.getFilterJson(), "type=groupcast时,filter_json必填");
                umengNotification.setPredefinedKeyValue("filter", pushDto.getFilterJson());
                break;
            case Customizedcast:
                Preconditions.checkNotNull(pushDto.getAlias_type(), "type=Customizedcast时, alias_type必填");
                umengNotification.setPredefinedKeyValue("alias_type", pushDto.getAlias_type());
                umengNotification.setPredefinedKeyValue("alias",pushDto.getAlias());
                break;
            case broadcast:
                break;
            default:
        }
        boolean flag = umengNotification.send();
        if (flag) {
            return ApiRes.Custom().success();
        } else {
            return ApiRes.Custom().failure();
        }
    }
}
