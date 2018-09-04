package cn.wind.xboot.tencent.controller;

import cn.wind.xboot.tencent.pojo.MsgInfo;
import cn.wind.xboot.tencent.pojo.Response.BaseRsp;
import cn.wind.xboot.tencent.service.IMService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/30 14:51
 * @Description:
 */
@RestController
@RequestMapping("/weapp/im")
public class IM {

    @Autowired
    private IMService imService;

    @ApiOperation("发送消息")
    @RequestMapping("sendMsg")
    public BaseRsp sendMsg(String userID, String token, String toUserId, List<MsgInfo> msgs){
        return imService.sendMsg(userID,token,toUserId,msgs);
    }

    @ApiOperation("拉黑")
    @RequestMapping("blackToUser")
    public BaseRsp blackToUser (String userID, String token, String toUserId){
        return imService.blackToUser(userID,token,toUserId);
    }
}
