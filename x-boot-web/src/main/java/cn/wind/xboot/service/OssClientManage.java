package cn.wind.xboot.service;

import cn.wind.xboot.config.AliOssConfiguration;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * <p>Title: OssClientManage</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/27
 */
@Service
public class OssClientManage {
    @Autowired
    private AliOssConfiguration config;

    /**
     * 文件流上传
     *
     * @Title uploadPic
     * @param @param
     *            file
     * @param @param
     *            keys
     * @param @return
     * @return String
     * @Explain
     */
    public String uploadPic(File file, String keys) {
        OSSClient ossClient = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
        ossClient.putObject(config.getBucket(), keys, file);
        String url = String.valueOf(getUrl(keys));
        ossClient.shutdown();
        return url;
    }

    /**
     * 文件流上传2
     *
     * @Title uploadPic
     * @param @param
     *            file
     * @param @param
     *            keys
     * @param @return
     * @return String
     * @Explain
     */
    public  String uploadPicLongTime(File file, String keys) {
        OSSClient ossClient = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
        ossClient.putObject(config.getBucket(), keys, file);
        String url = String.valueOf(getUrlLongTime(keys));
        ossClient.shutdown();
        return url;
    }

    /**
     * 字节数组上传
     *
     * @Title uploadPic
     * @param @param
     *            content
     * @param @param
     *            keys
     * @param @return
     * @return String
     * @Explain
     */
    public String uploadPic(byte[] content, String keys) {
        // 连接oss云存储服务器
        OSSClient ossClient = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
        ossClient.putObject(config.getBucket(), keys, new ByteArrayInputStream(content));
        String url = String.valueOf(getUrl(keys));
        ossClient.shutdown();
        return url;
    }

    /**
     * 获取文件URL
     *
     * @Title getUrl
     * @param @param
     *            key
     * @param @return
     * @return URL
     */
    public  String getUrl(String key) {
        if (key.indexOf("http") == -1) {
            // 连接oss云存储服务器
            OSSClient server = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
            // 设置URL过期时间为10年 3600l* 1000*24*365*10
            // url超时时间
            Date expirations = new Date(System.currentTimeMillis()+ 1000 * 60 * 5);
            // 生成URL
            URL url = server.generatePresignedUrl(config.getBucket(), key, expirations);
            String strUrl = String.valueOf(url);
            // 关闭client
            server.shutdown();
            return splitUrl(strUrl);
        } else {
            return splitUrl(key);
        }
    }

    /**
     * 获取文件URL
     *
     * @Title getUrl
     * @param @param key
     * @param @return
     * @return URL
     */
    public  String getUrlOneYeay(String key) {
        if (key.indexOf("http") == -1) {
            OSSClient server = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
            // 设置URL过期时间为1年 3600l* 1000*24*365
            Date expirations = new Date(System.currentTimeMillis()+ 3600l * 1000 * 24 * 365);

            // 生成URL
            URL url = server.generatePresignedUrl(config.getBucket(), key, expirations);
            String strUrl = String.valueOf(url);
            // 关闭client
            server.shutdown();
            return splitUrl(strUrl);
        } else {
            return splitUrl(key);
        }
    }

    /**
     * 获取文件URL
     *
     * @Title getUrl
     * @param @param
     *            key
     * @param @return
     * @return URL
     */
    public  String getUrlLongTime(String key) {
        if (key.indexOf("http") == -1) {
            OSSClient server = new OSSClient(config.getEndPoint(), config.getAccessKeyId(),config.getAccessKeySecret());
            // 设置URL过期时间为100年 3600l* 1000*24*365*100
            Date expirations = new Date(System.currentTimeMillis() + 3600l * 1000 * 24 * 365 * 100);

            // 生成URL
            URL url = server.generatePresignedUrl(config.getBucket(), key, expirations);
            String strUrl = String.valueOf(url);
            // 关闭client
            server.shutdown();
            return splitUrl(strUrl);
        } else {
            return splitUrl(key);
        }
    }

    /**
     * 切割url
     *
     * @param url
     * @return
     */
    public  String splitUrl(String url) {
        if (url.indexOf("?Expires") != -1) {
            return url.split("Expires")[0].substring(0,url.split("Expires")[0].length() -1 );
        }
        return url;
    }

    public String upload(InputStream is, String fileName) {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(config.getEndPoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        ossClient.putObject(config.getBucket(), fileName, is);
        // 关闭client
        ossClient.shutdown();
        String src ="http://"+ config.getBucket() + "." + config.getEndPoint() + "/" + fileName;
        return src;
    }

}
