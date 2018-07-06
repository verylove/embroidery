package cn.wind.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: AuthConfiguration</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/19
 */
@Configuration
@EnableConfigurationProperties(AuthorUrlConfiguration.class)
@ComponentScan("cn.wind.auth")
public class AuthConfiguration {
}
