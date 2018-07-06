package cn.wind.auth.config;

import cn.wind.auth.security.exception.CustomAuthExceptionEntryPoint;
import cn.wind.auth.security.exception.CustomAccessDeniedHandler;
import cn.wind.auth.security.handler.MyAccessDecisionManager;
import cn.wind.auth.security.handler.MyFilterSecurityInterceptor;
import cn.wind.auth.security.handler.MySecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * @author xukk
 * @Description: Oauth配置 资源服务器
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthorUrlConfiguration authorUrlConfiguration;
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor() {
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setSecurityMetadataSourceService(mySecurityMetadataSource);
        myFilterSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
        return myFilterSecurityInterceptor;
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .requestMatchers().antMatchers(authorUrlConfiguration.getAuthenticatedUrl().stream().toArray(String[]::new))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.addFilterAt(myFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.authenticationEntryPoint(new CustomAuthExceptionEntryPoint()).accessDeniedHandler(customAccessDeniedHandler);
    }
}
