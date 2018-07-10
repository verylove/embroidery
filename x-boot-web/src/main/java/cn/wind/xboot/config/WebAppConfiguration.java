package cn.wind.xboot.config;

import cn.wind.xboot.controller.intercepotor.JobPermissionInterceptor;
import cn.wind.xboot.controller.intercepotor.tokenInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <p>Title: WebAppConfig</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/25
 */
@EnableWebMvc
@Configuration
public class WebAppConfiguration implements WebMvcConfigurer {
    @Value("${spring.upload.freeRoot}")
    private String ROOT;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JobPermissionInterceptor jobPermissionInterceptor;

    @Bean
    public tokenInterceptor tokenInterceptor(){
        return new tokenInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/free/upload/**")
                .addResourceLocations("file:" + ROOT);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
    }
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false); // 关闭后缀服务器
//    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Optional o=converters.stream().filter(v->v.getClass().equals(MappingJackson2HttpMessageConverter.class)).findFirst();
        o.ifPresent(v->{
            ((MappingJackson2HttpMessageConverter) v).setObjectMapper(objectMapper);
        });
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jobPermissionInterceptor).addPathPatterns("/job/**","/admin/**").excludePathPatterns("/job/api");
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/api/app/**");
    }
}