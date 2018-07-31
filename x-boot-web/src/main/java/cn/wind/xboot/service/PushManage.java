package cn.wind.xboot.service;

import cn.wind.xboot.vo.PushVo;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.push.model.notification.*;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 20:17
 * @Description:
 */
@Service
public class PushManage {
    protected static final Logger LOG = LoggerFactory.getLogger(PushManage.class);

    public static void main(String[] args) {

    }

    // 使用 NettyHttpClient 异步接口发送请求
    public static void sendPushWithCallback(PushVo pushVo) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(pushVo.getAPP_KEY(), pushVo.getMASTER_SECRET()),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = null;
            switch (pushVo.getType()) {
                case 1:
                    //推送别名
                    payload = buildPushObject_android_and_ios(pushVo);
                    break;
                case 2:
                    //推送到IOS ANDORID winphone
                    payload = buildPushObject_all_all_alert(pushVo);
                    break;
                case 3:
                    //推送自定义信息
                    payload = buildPushObject_android_ios_aliasWithExtrasAndMessage(pushVo);
                    break;
                case 4:
                    payload = buildPushObject_android_ios_cid(pushVo);
                    break;
                default:
//                    return ApiRes.Custom().failure();
            }
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LOG.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static PushPayload buildPushObject_android_ios_aliasWithExtrasAndMessage(PushVo pushVo) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(pushVo.getALIAS_LIST()))
                .setMessage(Message.newBuilder().setMsgContent(pushVo.getMSG_CONTENT()).addExtras(pushVo.getExtra()).build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_android_ios_cid(PushVo pushVo) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(pushVo.getALIAS_LIST()))
                .setNotification(Notification.alert(pushVo.getALERT()))
                .setCid(pushVo.getIsCid() ? pushVo.getCid() : null)
                .build();
    }

//    /**
//     * 测试多线程发送 2000 条推送耗时
//     */
//    public static void testSendPushes() {
//        ClientConfig clientConfig = ClientConfig.getInstance();
//        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
//        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
//
//        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
//
//        jpushClient.getPushClient().setHttpClient(httpClient);
//        final PushPayload payload = buildPushObject_ios_tagAnd_alertWithExtrasAndMessage();
//        for(int i=0;i<10;i++) {
//            Thread thread = new Thread() {
//                public void run() {
//                    for (int j = 0; j < 200; j++) {
//                        long start = System.currentTimeMillis();
//                        try {
//                            PushResult result = jpushClient.sendPush(payload);
//                            LOG.info("Got result - " + result);
//
//                        } catch (APIConnectionException e) {
//                            LOG.error("Connection error. Should retry later. ", e);
//                            LOG.error("Sendno: " + payload.getSendno());
//
//                        } catch (APIRequestException e) {
//                            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//                            LOG.info("HTTP Status: " + e.getStatus());
//                            LOG.info("Error Code: " + e.getErrorCode());
//                            LOG.info("Error Message: " + e.getErrorMessage());
//                            LOG.info("Msg ID: " + e.getMsgId());
//                            LOG.error("Sendno: " + payload.getSendno());
//                        }
//
//                        System.out.println("耗时" + (System.currentTimeMillis() - start) + "毫秒 sendCount:" + (++sendCount));
//                    }
//                }
//            };
//            thread.start();
//        }
//    }

    //所有平台，所有设备，内容为 ALERT 的通知。
    public static PushPayload buildPushObject_all_all_alert(PushVo pushVo) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias( pushVo.getALIAS_LIST()))
                .setNotification(Notification.newBuilder()
                        .setAlert(pushVo.getALERT())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1).setSound("default").addExtras(pushVo.getExtra()).build())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(pushVo.getTITLE()).addExtras(pushVo.getExtra()).build())
                        .build())
                .build();
    }

    //设置别名
    public static PushPayload buildPushObject_android_and_ios(PushVo pushVo) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(pushVo.getALIAS_LIST()))
                .setNotification(Notification.newBuilder()
                        .setAlert(pushVo.getALERT())
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle(pushVo.getTITLE())
                                .addExtras(pushVo.getExtra()).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1).setSound("default")
                                .addExtra("extra_key", "extra_value").build())
                        .build()).setMessage((Message.newBuilder().setMsgContent(pushVo.getMSG_CONTENT()).addExtras(pushVo.getExtra()).build()))
                .build();
    }

    /**
     * 平台是 Andorid 与 iOS，
     * 推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集），
     * 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
     * @return
     */
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent("content")
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }
}
