package cn.wind.xboot.tencent.controller;


import cn.wind.xboot.tencent.logic.IMMgr;
import cn.wind.xboot.tencent.pojo.Request.GetLoginInfoReq;
import cn.wind.xboot.tencent.pojo.Response.GetLoginInfoRsp;
import cn.wind.xboot.tencent.pojo.Response.GetTestPushUrlRsp;
import cn.wind.xboot.tencent.pojo.Response.GetTestRtmpAccUrlRsp;
import cn.wind.xboot.tencent.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("/weapp/utils")
public class Util {
    @Resource
    IMMgr imMgr;

    @Autowired
    UtilService utilService;

    @ResponseBody
    @RequestMapping(value = "get_login_info")
    public GetLoginInfoRsp get_login_info(@ModelAttribute GetLoginInfoReq req){
        return imMgr.getLoginInfo(req.getUserID());
    }

    @ResponseBody
    @RequestMapping(value = "get_test_pushurl", method = RequestMethod.GET)
    public GetTestPushUrlRsp get_test_pushurl(){
        return utilService.getTestPushUrl();
    }

    @ResponseBody
    @RequestMapping("get_test_rtmpaccurl")
    public GetTestRtmpAccUrlRsp get_test_rtmpaccurl(){
        return utilService.getTestRtmpAccUrl();
    }
}
