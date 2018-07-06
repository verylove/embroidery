//package cn.wind.xboot.controller.common;
//
//import cn.wind.common.annotation.Page;
//import cn.wind.common.res.ApiRes;
//import cn.wind.common.utils.Assert;
//import cn.wind.mybatis.utils.SearchUtil;
//import cn.wind.xboot.message.push.PushDto;
//import cn.wind.xboot.message.push.Pusher;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import io.swagger.annotations.*;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.MediaType;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@Api(value = "/push", tags = "推送模块")
//@Slf4j
//@Validated
//@RestController
//@RequestMapping("push")
//public class PushController {
//    @Autowired
//    private Pusher pusher;
//    @Autowired
//    private IPfMessageTemplateService pfMessageTemplateService;
//
//
//    @ApiOperation(value = "获取模板列表", notes = "获取模板列表", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id_*_*", value = "资源ID", dataType = "Long", paramType = "path"),
//            @ApiImplicitParam(name = "title_*_*", value = "资源编码", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "type_*_*", value = "标题", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "content_*_*", value = "url地址", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "isDeleted_*_*", value = "排序", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "size", value = "每页大小", dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
//            @ApiImplicitParam(name = "sort", value = "排序", dataType = "String[]", paramType = "query"),
//    })
//    @GetMapping("template")
//    public ApiRes getTemplates(@ApiParam(hidden = true)@RequestParam Map<String, Object> map,
//                               @Page(maxSize = 20) @PageableDefault Pageable pageable) {
//        if(!map.containsKey("isDeleted"))
//        { map.put("isDeleted",0);}
//        com.baomidou.mybatisplus.plugins.Page<PfMessageTemplate> page = new com.baomidou.mybatisplus.plugins.Page(pageable.getPageNumber()+1,pageable.getPageSize());
//        EntityWrapper<PfSmsTemplate> ew=new EntityWrapper<>();
//        ew.setEntity(new PfSmsTemplate());
//        page=pfMessageTemplateService.selectPage(page, SearchUtil.autoBuildQuery(map,ew));
//        return ApiRes.Custom().success().addData(page.getRecords());
//    }
//    @ApiOperation(value = "更新模板", notes = "根据url的id来指定更新模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "资源ID", dataType = "Long",required = true,paramType = "path"),
//            @ApiImplicitParam(name = "title", value = "资源编码", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "type", value = "标题", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "content", value = "url地址", dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "isDeleted", value = "排序", dataType = "String", paramType = "query"),
//    })
//    @PutMapping("template/{id}")
//    @SneakyThrows
//    public ApiRes updateTemplate(@ApiParam(hidden = true)@RequestParam Map<String, Object> map,
//                                      @PathVariable(name="id") Integer templateId) {
//        PfMessageTemplate pfMessageTemplateEntity =pfMessageTemplateService.selectById(templateId);
//        Assert.checkNotNull(pfMessageTemplateEntity, "模板ID无效");
//        map.remove("id");
//        BeanUtils.populate(pfMessageTemplateEntity,map);
//       pfMessageTemplateService.updateById(pfMessageTemplateEntity);
//        return ApiRes.Custom().success();
//    }
//
//    @ApiOperation(value = "删除模板", notes = "根据url的id来指定删除指定模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @DeleteMapping("template/{id}")
//    public ApiRes deleteTemplate(@PathVariable(name="id") Integer templateId) {
//        PfMessageTemplate pfMessageTemplateEntity =pfMessageTemplateService.selectById(templateId);
//        Assert.checkNotNull(pfMessageTemplateEntity, "模板ID无效");
//        pfMessageTemplateService.deleteById(templateId);
//        return ApiRes.Custom().success();
//    }
//    @ApiOperation(value = "创建推送模板", notes = "根据title,type,content创建推送模板", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @PostMapping("template")
//    public ApiRes addTemplate(@RequestParam String title, @RequestParam Integer type, @RequestParam String content) {
//        PfMessageTemplate pfMessageTemplateEntity = new PfMessageTemplate();
//        pfMessageTemplateEntity.setContent(content);
//        pfMessageTemplateEntity.setTitle(title);
//        pfMessageTemplateEntity.setType(type);
//        pfMessageTemplateService.insert(pfMessageTemplateEntity);
//        return ApiRes.Custom().success();
//    }
//
//    @ApiOperation(value = "推送", notes = "根据参数进行推送\n"+
//            " * \"ticker\":\"xx\",     // 必填，通知栏提示文字\n" +
//            " * \"title\":\"xx\",      // 必填，通知标题\n" +
//            " * \"text\":\"xx\",       // 必填，通知文字描述\n" +
//            " * 点击\"通知\"的后续行为，默认为打开app。\n" +
//            " * \"after_open\": \"xx\" // 必填，值可以为:\n" +
//            " *      \"go_app\": 打开应用\n" +
//            " *      \"go_url\": 跳转到URL\n" +
//            " *      \"go_activity\": 打开特定的activity\n" +
//            " *      \"go_Custom\": 用户自定义内容。\n" +
//            " * \"display_type\":\"xx\",   // 必填，消息类型: notification(通知)、message(消息)\n" +
//            " * \"production_mode\":\"true/false\", // 可选，正式/测试模式。默认为true\n" +
//            " * 测试模式只会将消息发给测试设备。测试设备需要到web上添加。\n" +
//            " * Android: 测试设备属于正式设备的一个子集。\n" +
//            " * \"device_tokens\":\"xx\", // 当type=unicast时, 必填, 表示指定的单个设备\n" +
//            " * 当type=listcast时, 必填, 要求不超过500个, 以英文逗号分隔", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @PostMapping("send")
//    public ApiRes sendAndroidBroadcast(@Validated PushDto pushDto) {
//        return pusher.push(pushDto);
//    }
//
//
//}
