package cn.wind.xboot.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author FDjavaone
 * @date 2017/9/26
 */

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "xboot.alioss")
@Data
public class AliOssConfiguration {
    private String bucket;
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
}
