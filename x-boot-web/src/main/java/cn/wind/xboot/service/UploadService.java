package cn.wind.xboot.service;

/**
 * <p>Title: UploadService</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/27
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Title   UploadImageUtils.java
 * @Package com.pro.huanbao.common.utils
 * @author  wanpu_ly
 * @dade    2017年11月7日 上午10:06:57
 * @version V1.0
 * 类说明:	图片上传至OSS
 */
@Service
public class UploadService {
    @Autowired
    private OssClientManage ossClientManage;
    @Value("${server.port:}")
    private Integer port;
    @Value("${server.ip:127.0.0.1}")
    private String ip;
    /**
     * 返回的是阿里云的图片全路径名数组,需要可以换成网络路径
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public  List<String> fileUpload(MultipartFile[] file, HttpServletRequest request,String  path) throws Exception {
        List<String> listImagePath = new ArrayList<String>();
        for (MultipartFile mf : file) {
            if (!mf.isEmpty()) {
                // 生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                // 获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType = mf.getContentType();
                // 获得文件后缀名称
                String fileName = contentType.substring(contentType
                        .indexOf("/") + 1);
                fileName= uuid +"." + fileName;
                // 文件下暂存,上传至阿里云OSS后删除
                String date=DateUtil.format(new Date(), "yyyy-MM-dd");
                String[] strings = date.split("-");
                String fileName1 = strings[0]+"/"+strings[1]+"/"+strings[2]+"/"+fileName;
                String fullPath = path.endsWith("/")?(path+fileName1): (path.substring(0,path.length()-2)+fileName1);
                mf.transferTo( FileUtil.touch(fullPath));
                // 调用方法 把上传到本地的图片上传到OS
                String url = uploadOss(fullPath,fileName1);
                //String url=uploadLocal(date,fileName);
                listImagePath.add(url);
            }
        }
        return listImagePath;
    }
    public String uploadLocal(String date,String fileName){
            return "http://"+ip+":"+port+"/upload/"+date+"/"+fileName;
    }
    /**
     * 上传图片到阿里云OS
     * @Title   getImageUrl
     * @param   @param request
     * @param   @param path
     * @param   @return
     * @return  String
     * @Explain
     */
    public  String uploadOss(String path,String fileName) {
        File file = new File(path);
        // 图片网络路径
        String url = ossClientManage.uploadPicLongTime(file, fileName);
        // 删除本地图片
        file.delete();
        // 需求返回阿里云OSS文件全路径名, 需要可返回url
        return url;
    }
}
