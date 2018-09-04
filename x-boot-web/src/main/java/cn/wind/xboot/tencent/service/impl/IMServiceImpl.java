package cn.wind.xboot.tencent.service.impl;

import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.tencent.logic.IMMgr;
import cn.wind.xboot.tencent.pojo.MsgInfo;
import cn.wind.xboot.tencent.pojo.Response.BaseRsp;
import cn.wind.xboot.tencent.service.IMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/30 14:58
 * @Description:
 */
@Service
public class IMServiceImpl implements IMService {

    @Resource
    IMMgr imMgr;

    @Autowired
    private CXArUserManage userManage;

    private static Logger log= LoggerFactory.getLogger(IMServiceImpl.class);

    @Override
    public BaseRsp sendMsg(String userID, String token, String toUserId, List<MsgInfo> msgs) {
        BaseRsp rsp = new BaseRsp();
        if(userID == null || token == null || toUserId == null || msgs == null || msgs.size()<1){
            rsp.setCode(2);
            rsp.setMessage("请求失败，缺少参数");
            log.error("sendMsg失败:缺少参数：" + "userID：" + userID + ",token: " + token + ",toUserId: " + toUserId);
            return rsp;
        }

        if (imMgr.validation(userID, token) != 0) {
            rsp.setCode(7);
            rsp.setMessage("请求失败，鉴权失败");
            log.error("sendMsg失败:鉴权失败：" + "userID：" + userID);
            return rsp;
        }

        if(userManage.IsBlackInUserId(Long.parseLong(userID),Long.parseLong(toUserId))){
            rsp.setCode(90);
            rsp.setMessage("请求失败，您已被对方拉黑");
            log.error("sendMsg失败:您已被对方拉黑：" + "userID：" + userID);
            return rsp;
        }

        imMgr.sendMsg(Long.parseLong(userID),Long.parseLong(toUserId),msgs);
        return rsp;
    }

    @Override
    public BaseRsp blackToUser(String userID, String token, String toUserId) {
        BaseRsp rsp = new BaseRsp();
        if(userID == null || token == null || toUserId == null){
            rsp.setCode(2);
            rsp.setMessage("请求失败，缺少参数");
            log.error("blackToUser失败:缺少参数：" + "userID：" + userID + ",token: " + token + ",toUserId: " + toUserId);
            return rsp;
        }

        if (imMgr.validation(userID, token) != 0) {
            rsp.setCode(7);
            rsp.setMessage("请求失败，鉴权失败");
            log.error("blackToUser失败:鉴权失败：" + "userID：" + userID);
            return rsp;
        }

        if(userManage.IsBlackInUserId(Long.parseLong(toUserId),Long.parseLong(userID))){
            rsp.setCode(90);
            rsp.setMessage("请求失败，对方已被拉黑");
            log.error("blackToUser失败:对方已被拉黑：" + "userID：" + userID);
            return rsp;
        }

        try {
            userManage.blackToUser(Long.parseLong(userID),Long.parseLong(toUserId));
        } catch (Exception e) {
            rsp.setCode(0);
            rsp.setMessage("请求失败，处理异常");
            log.error("blackToUser失败，处理异常");
            return rsp;
        }
        return rsp;
    }
}
