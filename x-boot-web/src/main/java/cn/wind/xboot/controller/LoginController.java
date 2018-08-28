package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.klog.annotation.Log;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>Title: LoginController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/7/2
 */
@Controller
@Api(value = "login",tags = "登录管理")
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${server.port:}")
    private Integer port;

    @RequestMapping("oauth/login")
    @Log(value = "用户登录")
    public ResponseEntity login(HttpServletRequest request,String username, String password, String grant_type) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization","Basic YmFja1VzZXI6YmFja1VzZXI=");
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        //  也支持中文
        params.add("username", username);
        params.add("password", password);
        params.add("grant_type", grant_type);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        long starTime=System.currentTimeMillis();
        ResponseEntity s = restTemplate.postForEntity("http://127.0.0.1:" + port + "/oauth/token", requestEntity, Map.class);
        long endTime=System.currentTimeMillis();
        long Time=endTime-starTime;
        System.out.println(Time);
        return s;
    }
    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    @PostMapping("oauth/logout")
    @Log(value = "用户登出")
    public ApiRes logout(String accessToken) throws Exception {
          return ApiRes.Custom(consumerTokenServices.revokeToken(accessToken));
    }
}
