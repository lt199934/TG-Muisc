package net.ltbk.music.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

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


    public static void main(String[] args) {
        generate();
    }

    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/music?serverTimezone=UTC", "root", "199934lt")
                .globalConfig(builder -> {
                    builder.author("liutao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("F:\\项目\\springboot\\music\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tg.admin") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "F:\\项目\\springboot\\music\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok(); //开启lombok
                    builder.mapperBuilder().enableMapperAnnotation().build(); //加入@Mapper注解
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("article_category") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_","sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
