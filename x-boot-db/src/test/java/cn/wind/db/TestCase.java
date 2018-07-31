package cn.wind.db;

import cn.wind.mybatis.generator.MyBatisPlusGenerator;
import org.junit.Test;

/**
 * @author xukk
 * @date 2018/5/30
 */
public class TestCase {
    @Test
    public void test1() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("xukk")
                .url("jdbc:mysql://localhost:3306/xboot?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("root")
                .type("com.mysql.jdbc.Driver")
                .basePath("D:\\Projects\\github\\x-boot-parent\\x-boot-db")
                .outputDir("\\src\\main\\java")
                .base("sys")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"t_"})
                .tableNames(new String[]{"t_sys_user","t_sys_user_group","t_sys_user_group_ship","t_sys_user_group_role_ship"
                    ,"t_sys_user_info","t_sys_role","t_sys_role","t_sys_privilege","t_sys_permission"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }
    @Test
    public void test2() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("xukk")
                .url("jdbc:mysql://localhost:3306/xboot?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("root")
                .type("com.mysql.jdbc.Driver")
                .basePath("D:\\Projects\\github\\x-boot-parent\\x-boot-example\\x-boot-db")
                .outputDir("\\src\\main\\java")
                .base("cli")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"t_"})
                .tableNames(new String[]{"t_cli_client","t_cli_client_attempts"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }
    @Test
    public void test3() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("xukk")
                .url("jdbc:mysql://localhost:3306/xboot?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("root")
                .type("com.mysql.jdbc.Driver")
                .basePath("D:\\Projects\\github\\x-boot-parent\\x-boot-example\\x-boot-db")
                .outputDir("\\src\\main\\java")
                .base("sys")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"t_"})
                .tableNames(new String[]{"t_sys_log"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }
    @Test
    public void test4() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("xukk")
                .url("jdbc:mysql://localhost:3306/xboot?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("root")
                .type("com.mysql.jdbc.Driver")
                .basePath("D:\\Projects\\github\\x-boot-parent\\x-boot-example\\x-boot-db")
                .outputDir("\\src\\main\\java")
                .base("pf")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"t_"})
                .tableNames(new String[]{"t_pf_notice"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }

    @Test
    public void test5() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("changzhaoliang")
                .url("jdbc:mysql://localhost:3306/embroidery_dev?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("czl19950702")
                .type("com.mysql.jdbc.Driver")
                .basePath("/Users/changzhaoliang/Desktop/代码/embroidery__backstage/x-boot-db")
                .outputDir("/src/main/java")
                .base("sr")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"cx_"})
                .tableNames(new String[]{"cx_sr_levels"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }

    @Test
    public void test6() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("changzhaoliang")
                .url("jdbc:mysql://localhost:3306/embroidery_dev?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("czl19950702")
                .type("com.mysql.jdbc.Driver")
                .basePath("/Users/changzhaoliang/Desktop/代码/embroidery__backstage/x-boot-db")
                .outputDir("/src/main/java")
                .base("ar")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"cx_"})
                .tableNames(new String[]{"cx_ar_user_bank"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }

    @Test
    public void test7() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("changzhaoliang")
                .url("jdbc:mysql://localhost:3306/embroidery_dev?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("czl19950702")
                .type("com.mysql.jdbc.Driver")
                .basePath("/Users/changzhaoliang/Desktop/代码/embroidery__backstage/x-boot-db")
                .outputDir("/src/main/java")
                .base("rc")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"cx_"})
                .tableNames(new String[]{"cx_rc_second_pic"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }

    @Test
    public void test8() {
        MyBatisPlusGenerator.GeneratorDto generatorDto= MyBatisPlusGenerator.GeneratorDto.builder()
                .author("changzhaoliang")
                .url("jdbc:mysql://localhost:3306/embroidery_dev?characterEncoding=utf8&serverTimezone=Hongkong&useSSL=false")
                .username("root")
                .password("czl19950702")
                .type("com.mysql.jdbc.Driver")
                .basePath("/Users/changzhaoliang/Desktop/代码/embroidery__backstage/x-boot-db")
                .outputDir("/src/main/java")
                .base("ml")
                .packageName("cn.wind.db")
                .entityLombokModel(true)
                .prefixes(new String[]{"cx_"})
                .tableNames(new String[]{"cx_ml_user_goods_logistics"})
                .build();
        MyBatisPlusGenerator generator=new MyBatisPlusGenerator();
        generator.generateByTables(generatorDto);
    }
}
