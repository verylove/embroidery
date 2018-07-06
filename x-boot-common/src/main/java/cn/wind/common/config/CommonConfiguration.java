package cn.wind.common.config;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * <p>Title: CommonConfiguration</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/19
 */
@Configuration
@ComponentScan("cn.wind.common")
public class CommonConfiguration {
    @Value("${xboot.dozer.basename:}")
    String[] mappings;
    @Bean
    public DozerBeanMapper mapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Arrays.asList(mappings));
        return mapper;
    }
}
