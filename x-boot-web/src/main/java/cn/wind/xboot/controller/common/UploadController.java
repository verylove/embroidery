package cn.wind.xboot.controller.common;

import cn.hutool.core.io.FileUtil;
import cn.wind.common.res.ApiRes;
import cn.wind.xboot.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author xukk
 */
@Slf4j
@Controller
@Api(value ="upload",tags = "文件上传接口")
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Value("${spring.upload.authRoot:}")
    private String ROOT;
    @Resource
    private ResourceLoader resourceLoader;
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public ApiRes provideUploadInfo() throws IOException {
        return ApiRes.Custom().success().addData(Files.walk(Paths.get(ROOT))
                .filter(path -> !path.equals(Paths.get(ROOT)))
                .map(path -> Paths.get(ROOT).relativize(path))
                .map(path -> linkTo(methodOn(UploadController.class).getFile(path.toString())).withRel(path.toString()))
                .collect(Collectors.toList()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "/{date}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String date,@PathVariable String filename) {
        try {
            String[] strings = date.split("-");
            filename = strings[0]+"/"+strings[1]+"/"+strings[2]+"/"+filename;
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @RequestMapping("/img")
    public void getimg(String  dir,String fileName, HttpServletResponse response) throws IOException{
        try {
            StringBuffer dirPath=new StringBuffer();
            FileInputStream hFile=null;
            if(StringUtils.isNotBlank(dir)){
                Arrays.stream(dir.split("-")).forEach(v->dirPath.append("/"+v));
            }
            if(StringUtils.isBlank(dirPath.toString())){
                hFile=new FileInputStream(ROOT+fileName);
            }else {
                hFile =new FileInputStream(ROOT+dirPath.toString().substring(1,dirPath.toString().length())+fileName);
            }
            int i=hFile.available();
            byte[] data=new byte[i];
            hFile.read(data);
            hFile.close();
            response.setContentType("image/"+ FileUtil.extName(fileName));
            OutputStream toClient=response.getOutputStream();
            toClient.write(data);
            toClient.close();
        }catch (IOException e){
            PrintWriter toClient=response.getWriter();
            toClient.write("can not find '"+fileName+"'");
            toClient.close();
        }
    }
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "文件上传")
    public ApiRes upload(@RequestParam(value = "file", required = false) MultipartFile[] file,
                         HttpServletRequest request) {
        try {
            List<String> list = uploadService.fileUpload(file, request,ROOT);
            return ApiRes.Custom().success().addData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiRes.Custom().failure();
    }
}
