package net.ltbk.music.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;

import java.util.Collections;

/**
 * @Program: admin
 * @ClassName CodeGenerator
 * @Author: liutao
 * @Description: 代码生成器工具类
 * @Create: 2023-03-13 19:01
 * @Version 1.0
 **/

public class CodeGenerator {

    private static final String OUTPUT_DIR = System.getProperty("user.dir");
    public static void main(String[] args) {
        fastGenerate();
    }

    private static void generate() {
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/music?serverTimezone=UTC", "root", "199934lt")
                .build();
        // 加载配置
        AutoGenerator mpg = new AutoGenerator(dataSourceConfig);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .outputDir(OUTPUT_DIR + "/src/main/java")
                .author("liutao")
                .enableSwagger()
                .build();
        mpg.global(globalConfig);
        // 包配置
        TemplateConfig templateConfig = new TemplateConfig.Builder().build();


    }

    private static void fastGenerate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/music?serverTimezone=UTC", "root", "199934lt")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("liutao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            //F:\\项目\\springboot\\music\\src\\main\\java
                            .outputDir(OUTPUT_DIR + "/src/main/java"); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.tg.admin") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .entity("entity") // 设置实体类包名
                            .service("service") // 设置服务类包名
                            .serviceImpl("service.impl") // 设置服务实现类包名
                            .controller("controller") // 设置控制器包名
                            .mapper("mapper") // 设置Mapper.java包名
//                            .pathInfo(Collections.singletonMap(OutputFile.xml, "F:\\项目\\springboot\\music\\src\\main\\resources\\mapper"))
                            .pathInfo(Collections.singletonMap(OutputFile.xml, OUTPUT_DIR + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                // 模板配置
                .templateConfig(builder ->
                        builder.entity("templates/entity.java")
                                .service("templates/service.java")
                                .serviceImpl("templates/serviceImpl.java")
                                .mapper("templates/mapper.java")
                                .xml("templates/mapper.xml")
                                .controller("templates/controller.java")
                )
                // 策略配置
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //开启lombok
                    builder.mapperBuilder().enableMapperAnnotation().build(); //加入@Mapper注解
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
