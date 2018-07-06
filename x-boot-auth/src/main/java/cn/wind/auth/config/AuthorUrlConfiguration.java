package cn.wind.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>Title: AuthorUrlConfiguration</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/21
 */
@Data
@ConfigurationProperties(prefix = "xboot")
public class AuthorUrlConfiguration {
    private List<String> ignoredUrl;
    private List<String> authenticatedUrl;
}
