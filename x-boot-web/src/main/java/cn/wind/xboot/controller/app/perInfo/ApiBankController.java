package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArUserBank;
import cn.wind.db.ar.service.IArUserBankService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.app.CXArBankManage;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/24 16:51
 * @Description:
 */
@Api(value = "银行卡管理",tags = "银行卡管理")
@RestController
@RequestMapping("/api/app/bank")
public class ApiBankController extends AppBaseController{

    @Autowired
    private IArUserBankService bankService;
    @Autowired
    private CXArBankManage bankManage;

    @ApiOperation(value = "银行卡 分页")
    @GetMapping("/pageInBank")
    public ApiRes pageInBank(@ModelAttribute PageVo<ArUserBank> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("type,desc");
            sort.add("create_time,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserBank> ew=new EntityWrapper<ArUserBank>();
            ew.eq("user_id",getUserId()).and().eq("status",1);
            Page<ArUserBank> page =bankService.selectPage(pageVo.initPage(),ew);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

//    @ApiOperation(value = "添加银行卡")
//    public ApiRes addBankCard(){
//
//    }

    @ApiOperation(value = "银行卡 删除")
    @ApiImplicitParam(name = "bankId",value = "银行卡ID",dataType = "Long",required = true,paramType = "query")
    @DeleteMapping("/deleteBankCard")
    public ApiRes deleteBankCard(Long bankId){
        try{
            bankManage.delete(bankId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "银行卡 设置默认")
    @ApiImplicitParam(name = "bankId",value = "银行卡ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/defaultBankCard")
    public ApiRes defaultBankCard(Long bankId){
        try{
            bankManage.defaultBankCard(bankId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取默认银行卡")
    @GetMapping("/getDefaultBank")
    public ApiRes getDefaultBank(){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("type",1);
            map.put("status",1);
            ArUserBank bank = bankService.findOneByConditons(map);
            return ApiRes.Custom().addData(bank);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
