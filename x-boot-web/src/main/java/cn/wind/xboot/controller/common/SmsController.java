package cn.wind.xboot.controller.common;

import cn.wind.common.domain.Statistics;
import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.Assert;
import cn.wind.db.pf.entity.PfSms;
import cn.wind.db.pf.service.IPfSmsLogService;
import cn.wind.db.pf.service.IPfSmsService;
import cn.wind.mybatis.utils.SearchUtil;
import cn.wind.xboot.message.sms.dto.SmsSendReq;
import cn.wind.xboot.message.sms.enums.SmsTypeEnum;
import cn.wind.xboot.message.sms.service.SmsService;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.pf.PfSmsVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author xukk
 */
@Api(value = "/SmsParam", tags = "短信模块")
@Slf4j
@Validated
@RestController
@RequestMapping("sms")
public class SmsController {
    @Autowired
    private IPfSmsService pfSmsService;
    @Autowired
    private IPfSmsLogService pfSmsLogService;
    @Autowired
    protected DozerBeanMapper beanMapper;
    @Autowired
    private SmsService smsService;
    @ApiOperation(value = "推送", notes ="短信推送",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping("send")
    @SuppressWarnings("unchecked")
    public ApiRes sendSMS(@RequestParam String mobiles, @RequestParam Long templateId) {
        ApiRes apiResponse = ApiRes.Custom();
        PfSms pfSms =pfSmsService.selectById(templateId);
        Assert.checkNotNull(pfSms, "模板ID无效");
        List<String> list = Arrays.asList(mobiles.split(","));
        SmsTypeEnum smsTypeEnum=SmsTypeEnum.valueOfOrNull(pfSms.getName());

        String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        list.forEach(v -> {
            String a = v.trim();
            Assert.checkArgument(a.matches(pattern), a + " 无效手机号");
        });
        Map<String, Integer> map = Maps.newHashMap();
        Statistics statistics=Statistics.builder().total(list.size()).build();
        JsonObject jsonObject=new JsonObject();
        switch (smsTypeEnum) {
            case SIGN_IN:
                //登陸
                break;
            case SIGN_UP:
                //注冊
                break;
            case FORGET:
                break;
            case NULL:
            default:
                break;
        }
        list.forEach(v -> {
            ApiRes res=smsService.sendSms(SmsSendReq.builder().phoneNumbers(v).templateCode(pfSms.getTemplateCode()).templateParam(jsonObject.toString()).build());
            if(res.valid()){
                statistics.getAvi().incrementAndGet();
            }else {
                log.warn("send sms error:"+res.getMessage());
            }
        });
        return  apiResponse.success().addData(statistics);
    }
    @ApiOperation(value = "余额查询", notes ="余额查询",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @GetMapping("queryBalance")
    @SuppressWarnings("unchecked")
    public ApiRes queryBalance() {
            return ApiRes.Custom().success();
    }
    @ApiOperation(value = "获取模板列表", notes = "获取模板列表", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id_*_*", value = "资源ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "title_*_*", value = "资源编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type_*_*", value = "标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content_*_*", value = "url地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isDeleted_*_*", value = "排序", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页大小", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", dataType = "String[]", paramType = "query"),
    })
    @GetMapping("template")
    @SuppressWarnings("unchecked")
    public ApiRes getTemplates(@ModelAttribute Map<String,Object> map,
                               @ModelAttribute PageVo pageVo) {
        EntityWrapper<PfSms> ew=new EntityWrapper<>();
        Page page=pfSmsService.selectPage(pageVo.initPage(), SearchUtil.autoBuildQuery(map,ew));
        return ApiRes.Custom().success().addData(page);
    }
    @ApiOperation(value = "更新模板", notes = "根据url的id来指定更新模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PutMapping("template")
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public ApiRes updateTemplate(@ApiParam(hidden = true) PfSmsVo smsVo) {
        PfSms pfSms = pfSmsService.selectById(smsVo.getId());
        Assert.checkNotNull(pfSms, "模板ID无效");
        PfSms pfSms1=beanMapper.map(smsVo,PfSms.class);
        pfSmsService.updateById(pfSms1);
        return ApiRes.Custom().success();
    }
    @ApiOperation(value = "删除模板", notes = "根据url的id来指定删除指定模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @DeleteMapping("template/{id}")
    @SuppressWarnings("unchecked")
    public ApiRes deleteTemplate(@PathVariable(name="id") Integer templateId) {
        PfSms pfSms = pfSmsService.selectById(templateId);
        Assert.checkNotNull(pfSms, "模板ID无效");
        pfSmsService.deleteById(templateId);
        return ApiRes.Custom().success();
    }
    @ApiOperation(value = "创建推送模板", notes = "根据title,type,content创建推送模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping("template")
    @SuppressWarnings("unchecked")
    public ApiRes addTemplate(@ModelAttribute PfSmsVo smsVo) {
        PfSms pfSms=beanMapper.map(smsVo,PfSms.class);
        pfSmsService.insert(pfSms);
        return ApiRes.Custom().success();
    }
}
