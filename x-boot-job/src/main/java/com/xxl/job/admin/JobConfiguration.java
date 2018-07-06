package com.xxl.job.admin;

import com.xxl.job.admin.service.TestService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>Title: JobConfiguration</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/19
 */
@Configuration
@ComponentScan(basePackages={"com.xxl.job.admin"})
@MapperScan("com.xxl.job.*.dao")
@PropertySource(value = "classpath:xxl-job-admin.properties")
public class JobConfiguration {
    @Bean
    public TestService testService(){
        return new TestService();
    }
}
