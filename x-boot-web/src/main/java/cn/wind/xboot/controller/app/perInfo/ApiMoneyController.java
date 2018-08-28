package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserMoneyRecord;
import cn.wind.db.ar.service.IArUserMoneyRecordService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.app.CXArUserMoneyManage;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/24 15:50
 * @Description:
 */
@Api(value = "钱包管理",tags = "钱包管理")
@RestController
@RequestMapping("/api/app/money")
public class ApiMoneyController extends AppBaseController{

    @Autowired
    private IArUserMoneyRecordService moneyRecordService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXArUserMoneyManage userMoneyManage;


    @ApiOperation(value = "全部消费记录")
    @GetMapping("/pageInMoneyRecord")
    public ApiRes pageInMoneyRecord(@ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            ew.eq("user_id",getUserId()).and().eq("status",2);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "全部增加的消费记录")
    @GetMapping("/pageInMoneyRecordPlus")
    public ApiRes pageInMoneyRecordPlus(@ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            Integer[] types = {1,4};
            ew.eq("user_id",getUserId()).and().eq("status",2).and().in("type",types);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);

            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "全部消费的消费记录")
    @GetMapping("/pageInMoneyRecordMinus")
    public ApiRes pageInMoneyRecordMinus(@ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            Integer[] types = {2,3};
            ew.eq("user_id",getUserId()).and().eq("status",2).and().in("type",types);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "已提现的提现记录")
    @GetMapping("/pageInWithdraw")
    public ApiRes pageInWithdraw(@ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            ew.eq("user_id",getUserId()).and().eq("status",2).and().eq("type",3);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "正在审核的提现记录")
    @GetMapping("/pageInWithdrawIng")
    public ApiRes pageInWithdrawIng(@ModelAttribute PageVo<ArUserMoneyRecord> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMoneyRecord> ew=new EntityWrapper<ArUserMoneyRecord>();
            ew.eq("user_id",getUserId()).and().eq("status",1).and().eq("type",3);
            Page<ArUserMoneyRecord> page =moneyRecordService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().addData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "amount",value = "金额",dataType = "BigDecimal",required = true,paramType = "query"),
            @ApiImplicitParam(name = "bankId",value = "银行卡ID",dataType = "Long",required = true,paramType = "query")
    })
    @PostMapping("/withdraw")
    public ApiRes withdraw(BigDecimal amount, Long bankId){
        try{
            ArUser user = userService.selectById(getUserId());
            if(user.getBalance().compareTo(amount)<0){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            //最低提现1000刺币，即100元
            if(amount.compareTo(BigDecimal.valueOf(1000))<0){
                return ApiRes.Custom().failure(ApiStatus.WITHDRAW_LOW_PRICE);
            }
            userMoneyManage.withdraw(amount,bankId,user);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }
}
