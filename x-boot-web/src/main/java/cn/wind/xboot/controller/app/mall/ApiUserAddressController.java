package cn.wind.xboot.controller.app.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ml.entity.MlUserAddress;
import cn.wind.db.ml.service.IMlUserAddressService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ml.mlUserAddressDto;
import cn.wind.xboot.service.app.CXMlUserAddressManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ml.MlUserAddressVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/25 19:19
 * @Description:
 */
@Api(value = "用户收件地址管理",tags = "用户收件地址管理")
@RestController
@RequestMapping("/api/app/userAddress")
public class ApiUserAddressController extends AppBaseController{

    @Autowired
    private IMlUserAddressService addressService;
    @Autowired
    private CXMlUserAddressManage addressManage;

    @ApiOperation(value = "用户默认收件地址")
    @GetMapping("/defaultUserAddress")
    public ApiRes defaultUserAddress(){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("type",1);
            map.put("status",1);
            MlUserAddress address = addressService.findOneByConditions(map);

            MlUserAddressVo vo = new MlUserAddressVo();
            BeanUtils.copyProperties(address,vo);
            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户收件地址 分页")
    @GetMapping("/pageInUserAddress")
    public ApiRes pageInUserAddress(@ModelAttribute PageVo<MlUserAddress> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("type,desc");
            sort.add("create_time,desc");
            pageVo.setSort(sort);
            EntityWrapper<MlUserAddress> ew=new EntityWrapper<MlUserAddress>();
            ew.eq("user_id",getUserId()).and().eq("status",1);
            Page<MlUserAddress> page = addressService.selectPage(pageVo.initPage(),ew);

            List<MlUserAddressVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),MlUserAddressVo.class);

            Page<MlUserAddressVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "新增用户收件地址")
    @PostMapping("/addUserAddress")
    public ApiRes addUserAddress(mlUserAddressDto dto){
        try{
            addressManage.addUserAddress(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "修改用户收件地址")
    @ApiImplicitParam(name = "userAddressId",value = "收件地址ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/modifyUserAddress")
    public ApiRes modifyUserAddress(mlUserAddressDto dto,Long userAddressId){
        try{
            addressManage.modifyUserAddress(dto,userAddressId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "删除用户收件地址")
    @ApiImplicitParam(name = "userAddressId",value = "收件地址ID",dataType = "Long",required = true,paramType = "query")
    @DeleteMapping("/deleteUserAddress")
    public ApiRes deleteUserAddress(Long userAddressId){
        try{
            addressManage.deleteUserAddress(userAddressId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }
}
