package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/19 16:33
 * @Description:
 */
@Api(value = "masterShow",tags = "masterShow")
@RestController
@RequestMapping("/api/app/MstShow")
public class ApiMasterShowController extends AppBaseController{

    @Autowired
    private IArUserService userService;
    @Autowired
    private CXArUserManage userManage;

    @ApiOperation(value = "master show 分页")
    @GetMapping("/pageInMasterShow")
    public ApiRes pageInMasterShow(@ModelAttribute PageVo<ArUser> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("praise_num,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUser> ew=new EntityWrapper<ArUser>();
            ew.eq("name_status",3);

            Page<ArUser> list =userService.selectPage(pageVo.initPage(),ew);

            List<ArUserVo> users = ObjectMapperUtils.mapAll(list.getRecords(),ArUserVo.class);

            Page<ArUserVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());
            for(ArUserVo user:users){
                if(userManage.IsgreatInAppForOneUser(getUserId(),user.getId())){
                    user.setIsGreat(1);
                }else {
                    user.setIsGreat(0);
                }
            }
            voPage.setRecords(users);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }


}
