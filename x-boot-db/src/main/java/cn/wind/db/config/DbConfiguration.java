package cn.wind.db.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: DbConfiguration</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/19
 */
@Configuration
@ComponentScan("cn.wind.db")
@MapperScan(value = {"cn.wind.db.*.dao"})
public class DbConfiguration {
}
