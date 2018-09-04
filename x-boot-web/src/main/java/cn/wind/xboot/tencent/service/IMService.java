package cn.wind.xboot.tencent.service;

import cn.wind.xboot.tencent.pojo.MsgInfo;
import cn.wind.xboot.tencent.pojo.Response.BaseRsp;

import java.util.List; /**
 * @Auther: changzhaoliang
 * @Date: 2018/8/30 14:57
 * @Description:
 */
public interface IMService {

    BaseRsp sendMsg(String userID, String token, String toUserId, List<MsgInfo> msgs);

    BaseRsp blackToUser(String userID, String token, String toUserId);
}
