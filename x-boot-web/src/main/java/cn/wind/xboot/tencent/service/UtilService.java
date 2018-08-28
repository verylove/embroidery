package cn.wind.xboot.tencent.service;

import cn.wind.xboot.tencent.pojo.Request.GetPushUrlReq;
import cn.wind.xboot.tencent.pojo.Response.*;

import java.util.Map;

public interface UtilService {
    GetPushUrlRsp getPushUrl(String userID, String token);

    GetPushUrlRsp getPushUrl(String userID, String token, GetPushUrlReq req);

    MergeStreamRsp mergeStream(String userID, String token, Map map);

    GetTestPushUrlRsp getTestPushUrl();

    GetTestRtmpAccUrlRsp getTestRtmpAccUrl();
}
