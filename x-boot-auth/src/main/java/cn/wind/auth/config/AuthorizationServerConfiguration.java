package cn.wind.auth.config;

import cn.wind.auth.config.token.MyRedisTokenStore;
import cn.wind.auth.security.exception.CustomWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author xukk
 * @date 2018/3/14
 * @Description: Oauth配置 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager ;//AuthenticationManager  - 我们添加一个  @Qualifier来查找在ServerSecurityConfiguration  类中定义的bean
    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;
    @Resource
    private DataSource dataSource;
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    private MyRedisTokenStore redisTokenStore;
    @Autowired(required = false)
    private JwtTokenStore jwtTokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        if(jwtTokenStore!=null){
            endpoints.tokenStore(jwtTokenStore).authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService);
        }else if(redisTokenStore!=null){
            endpoints.tokenStore(redisTokenStore).authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService);
        }
        if(jwtAccessTokenConverter!=null){
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
    }

    @Bean
    public OAuth2AccessDeniedHandler accessDeniedHandler()
    {
        OAuth2AccessDeniedHandler adh = new OAuth2AccessDeniedHandler();
        adh.setExceptionRenderer(new DefaultOAuth2ExceptionRenderer());
        return adh;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
        security.accessDeniedHandler(accessDeniedHandler());
        OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        oAuth2AuthenticationEntryPoint.setRealmName("oauth2/client");
        oAuth2AuthenticationEntryPoint.setExceptionRenderer(new DefaultOAuth2ExceptionRenderer());
        security.authenticationEntryPoint(oAuth2AuthenticationEntryPoint);
        security.allowFormAuthenticationForClients();
    }
}
