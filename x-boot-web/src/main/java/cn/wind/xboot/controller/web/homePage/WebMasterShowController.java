package cn.wind.xboot.controller.web.homePage;

import cn.wind.xboot.controller.BaseController;
import com.baomidou.mybatisplus.service.IService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/5 16:11
 * @Description:
 */
@Api(value = "MasterShow管理",tags = "MasterShow管理")
@RestController
@RequestMapping("/webSys/MstShow")
public class WebMasterShowController extends BaseController{

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }
}
