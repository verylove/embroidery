package cn.wind.xboot.controller.common;

import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.IpInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(value ="ip",tags = "IP接口")
@RequestMapping("/api/common/ip")
public class IpInfoController {

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value = "IP及天气相关信息")
    public ApiRes upload(HttpServletRequest request) {
        String result= IpInfoUtil.getIpWeatherInfo(IpInfoUtil.getIpAddr(request));
        return ApiRes.Custom().success().addData(result);
    }
}