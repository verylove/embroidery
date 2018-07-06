package cn.wind.xboot.controller.common;

import cn.wind.common.res.ApiRes;
import cn.wind.xboot.message.email.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>Title: EmailController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/28
 */
@Controller
@Api(value = "emai",tags = "邮件管理")
@RequestMapping("email")
public class EmailController {
    @Resource
    private EmailService emailService;

    /**
     * 测试邮件发送
     */
    @ApiOperation(value="测试邮件发送", notes="getEntityById")
    @RequestMapping(value = "/getTestDemoEmail", method = RequestMethod.GET)
    public @ResponseBody
    ApiRes getEntityById() throws Exception {
        String sendTo = "410551654@qq.com";
        String titel = "测试邮件标题";
        String content = "测试邮件内容";
        emailService.sendSimpleMail(sendTo, titel, content);
        return ApiRes.Custom().success();
    }

}
