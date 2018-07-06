package cn.wind.xboot.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>Title: ApiController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/19
 */
@Controller
@RequestMapping("/api/open")
public class ApiController {

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "test";
    }

}
