package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.HttpClientUtil;
import cn.wind.db.ar.entity.ArUserBank;
import cn.wind.db.ar.service.IArUserBankService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserBankDto;
import cn.wind.xboot.service.app.CXArBankManage;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "银行卡 分页")
    @GetMapping("/pageInBank")
    public ApiRes pageInBank(@ModelAttribute PageVo<ArUserBank> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("type,desc");
            sort.add("createTime,desc");
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

    @ApiOperation(value = "添加银行卡")
    @PostMapping("/addBankCard")
    public ApiRes addBankCard(arUserBankDto dto){
        try{
            //银行卡四要素验证
            String host = "https://jisubank4.market.alicloudapi.com";
            String path = "/bankcardverify4/verify";
            String method = "GET";
            String appcode = "7a579ef0b6694aa385bede6903f75bac";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("bankcard", dto.getBankCard());
            querys.put("idcard", dto.getIdentityCard());
            querys.put("mobile", dto.getPhone());
            querys.put("realname", dto.getName());
            HttpResponse response = HttpClientUtil.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            JSONObject jsStr = JSONObject.fromObject(result.toString());
            if(!jsStr.getString("status").equals("0")){
                return ApiRes.Custom().failure(jsStr.getString("msg"));
            }else {
                JSONObject re = jsStr.getJSONObject("result");
                if(re.getString("verifystatus").equals("1")){
                    return ApiRes.Custom().failure(re.getString("verifymsg"));
                }
            }

            //2.判断验证码是否正确
            String code = redisTemplate.opsForValue().get("CIXIU_PHONE_BANK:"+dto.getPhone());
            if(code==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(dto.getCode())){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            bankManage.addBankCard(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "银行卡 删除")
    @ApiImplicitParam(name = "bankId",value = "银行卡ID",dataType = "Long",required = true,paramType = "query")
    @DeleteMapping("/deleteBankCard")
    public ApiRes deleteBankCard(Long bankId){
        try{
            bankManage.delete(getUserId(),bankId);
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
