package cn.wind.xboot.tencent.logic;

import cn.wind.common.utils.MathUtil;
import cn.wind.xboot.tencent.common.Config;
import cn.wind.xboot.tencent.pojo.MsgInfo;
import cn.wind.xboot.tencent.pojo.Request.CreateGroupReq;
import cn.wind.xboot.tencent.pojo.Request.DestroyGroupReq;
import cn.wind.xboot.tencent.pojo.Request.NotifyPusherChangeReq;
import cn.wind.xboot.tencent.pojo.Request.SendMsgReq;
import cn.wind.xboot.tencent.pojo.Response.GetLoginInfoRsp;
import cn.wind.xboot.tencent.pojo.Response.LoginRsp;
import cn.wind.xboot.tencent.utils.Utils;
import cn.wind.xboot.tencent.tls.tls_sigature.tls_sigature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IMMgr {
    private static Logger log= LoggerFactory.getLogger(IMMgr.class);

    private static final String HOST = "https://console.tim.qq.com/"; // IM后台RESTful API的主机地址。

    private ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

    @Autowired
    RestTemplate restTemplate;

    public LoginRsp verifySig(String sdkAppID, String accountType, String userID, String userSig) {
        LoginRsp rsp = new LoginRsp();
        if (sdkAppID == null || accountType == null || userID == null || userSig == null) {
            rsp.setCode(4);
            rsp.setMessage("请求参数不全，请检查sdkAppID，IM_ACCOUNTTYPE，userID，userSig参数是否都存在");
            log.error("verifySig请求参数不全，IM_SDKAPPID:" + sdkAppID + ", IM_ACCOUNTTYPE:" + accountType + ", userID:" + userID + ", userSig:" + userSig);
        } else {
            try {
                tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(userSig, Long.valueOf(sdkAppID), userID, Config.IM.PUBLICKEY);
                if (checkResult.verifyResult == false) {
                    rsp.setCode(7);
                    rsp.setMessage("鉴权失败, " + checkResult.errMessage);
                    log.error("verifySig鉴权失败, " + checkResult.errMessage);
                } else {
                    rsp.setCode(0);
                    rsp.setMessage("请求成功");
                    rsp.setUserID(userID);
                    String token = makeToken(sdkAppID, accountType, Config.Live.APIKEY, userID);
                    tokenMap.put(userID, token);
                    rsp.setToken(token);
                    log.info("verifySig鉴权成功");
                }
            } catch (Exception e) {
                rsp.setCode(4);
                rsp.setMessage("签名校验出错，" + e.getMessage());
                log.error("verifySig签名校验出错, " + e.getMessage());
                e.printStackTrace();
            }

        }
        return rsp;
    }


    /**
     * 构建一个token = md5(APIKEY+APP_ID+IM_ACCOUNTTYPE+userID+radom)
     */
    private String makeToken(String appID, String accountType, String apiKey, String userID) {
        String Token = Utils.getMD5(apiKey + appID + accountType + userID + Utils.S4() + Utils.S4() + Utils.S4());
        return Token;
    }

    public GetLoginInfoRsp getLoginInfo(String userID) {
        GetLoginInfoRsp rsp = new GetLoginInfoRsp();
        try {
            if (userID.length() == 0) {
                userID = Utils.genUserIdByRandom();
            }
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(Config.IM.IM_SDKAPPID, userID, Config.IM.PRIVATEKEY, 30 * 24 * 60 * 60);
            if (0 == result.urlSig.length()) {
                rsp.setCode(1);
                rsp.setMessage(result.errMessage);
                log.error("getLoginInfo生成usersig失败, userID: " + userID + ", errMsg: " + result.errMessage);
            } else {
                rsp.setCode(0);
                rsp.setMessage("请求成功");
                rsp.setSdkAppID(Config.IM.IM_SDKAPPID);
                rsp.setAccType(Config.IM.IM_ACCOUNTTYPE);
                rsp.setUserID(userID);
                rsp.setUserSig(result.urlSig);
                log.info("getLoginInfo成功, userID:" + userID);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("getLoginInfo出现异常, userID: " + userID + ", errMsg: " + e.getMessage());
        }
        return rsp;
    }

    public int validation(String userID, String token) {
        int ret = 1;
        if (tokenMap.containsKey(userID) && tokenMap.get(userID).equals(token)) {
            ret = 0;
        }
        return ret;
    }

    public void delLoginSession(String userID) {
        if (tokenMap.containsKey(userID)) {
            tokenMap.remove(userID);
        }
    }

    /**
     * 建群 - 参考@https://cloud.tencent.com/document/product/269/1615
     */
    public boolean createGroup(String groupID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/group_open_http_svc/create_group" + getQueryString());

        CreateGroupReq req = new CreateGroupReq();
        req.setGroupId(groupID);
        req.setName("group_name");
        req.setType("AVChatRoom");
        req.setOwner_Account(Config.IM.ADMINISTRATOR);

        HttpEntity<String> entity = new HttpEntity<String>(Utils.objectToString(req), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);

        log.info("createGroup, groupID: " + groupID + ", body: " + response.toString());
        if (response.getStatusCode().value() == HttpStatus.OK.value()){
            return true;
        } else {
            log.error("createGroup失败, groupID: " + groupID + ", errMsg: " + response.toString());
            return false;
        }
    }

    private String getQueryString() {
        String query =
                "?sdkappid=" + Config.IM.IM_SDKAPPID +
                        "&identifier=" + Config.IM.ADMINISTRATOR +
                        "&usersig=" + getLoginInfo(Config.IM.ADMINISTRATOR).getUserSig() +
                        "&random=" + UUID.randomUUID().toString() +
                        "&contenttype=json";
        return query;
    }

    /**
     * 解散群 - 参考@https://cloud.tencent.com/document/product/269/1624
     */
    public void destroyGroup(String groupID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/group_open_http_svc/destroy_group" + getQueryString());

        DestroyGroupReq req = new DestroyGroupReq();
        req.setGroupId(groupID);

        HttpEntity<String> entity = new HttpEntity<String>(Utils.objectToString(req), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("destroyGroup, groupID: " + groupID + ", body: " + response.toString());
        if (response.getStatusCode().value() != HttpStatus.OK.value()){
            log.error("destroyGroup失败, groupID: " + groupID + ", errMsg: " + response.toString());
        }
    }

    /**
     * 通知房间成员变动 - 参考@https://cloud.tencent.com/document/product/269/1630
     */
    public void notifyPushersChange(String groupID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/group_open_http_svc/send_group_system_notification" + getQueryString());

        NotifyPusherChangeReq req = new NotifyPusherChangeReq();
        req.setGroupId(groupID);
        NotifyPusherChangeReq.Content content = new NotifyPusherChangeReq.Content();
        content.setCmd("notifyPusherChange");
        content.setData("");
        req.setContent(Utils.objectToString(content));

        HttpEntity<String> entity = new HttpEntity<String>(Utils.objectToString(req), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("notifyPushersChange, groupID: " + groupID + ", body: " + response.toString());
        if (response.getStatusCode().value() != HttpStatus.OK.value()){
            log.error("notifyPushersChange失败, groupID: " + groupID + ", errMsg: " + response.toString());
        }
    }


    public void sendMsg(Long fromUserId, Long toUserId, List<MsgInfo> msgs){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/openim/sendmsg" + getQueryString());

        SendMsgReq req = new SendMsgReq();
        req.setSyncOtherMachine(1);
        req.setFrom_Account(fromUserId.toString());
        req.setTo_Account(toUserId.toString());
        req.setMsgRandom(MathUtil.getRandomNum(1,9999999));
        JSONArray MsgBody = new JSONArray();
        for(MsgInfo msgInfo:msgs){
            JSONObject msg = new JSONObject();
            if(msgInfo.getType()==1){//文字
                msg.put("MsgType","TIMTextElem");
                JSONObject content = new JSONObject();
                content.put("Text",msgInfo.getContent());
                msg.put("MsgContent",content);
                MsgBody.add(msg);
            }else {//表情
                msg.put("MsgType","TIMFaceElem");
                JSONObject content = new JSONObject();
                content.put("Index",msgInfo.getIndex());
                content.put("Data",msgInfo.getContent());
                msg.put("MsgContent",content);
                MsgBody.add(msg);
            }
        }
        req.setMsgBody(MsgBody);

        HttpEntity<String> entity = new HttpEntity<String>(Utils.objectToString(req), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("sendMsg, from: " + fromUserId + ", to: " + toUserId + ", body: " + response.toString());
        if (response.getStatusCode().value() != HttpStatus.OK.value()){
            log.error("sendMsg失败, from: " + fromUserId + ", to: " + toUserId + ", errMsg: " + response.toString());
        }
    }


}
