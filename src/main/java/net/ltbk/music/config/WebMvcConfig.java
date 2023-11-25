package net.ltbk.music.config;

import net.ltbk.music.common.LoginInterceptor;
import net.ltbk.music.formatter.StringToDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;


/**
 * @Program: music
 * @ClassName WebMvcCofig
 * @Author: liutao
 * @Description: mvc配置
 * @Create: 2023-03-09 13:50
 * @Version 1.0
 **/
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 绝对路径
     **/
    private static String absolutePath = "";
    /**
     * 静态目录
     **/
    private static String staticDir = "static";
    /**
     * 文件存放的目录
     **/
    private static String fileDir = "/upload/";
    @Autowired
    private StringToDateFormatter stringToDateFormatter;

    /***
     * @MethodName: addInterceptors
     * @description: 拦截器配置
     * @Author: LiuTao
     * @Param: [registry]
     * @UpdateTime: 2023/3/9 19:52
     * @Return: void
     * @Throw:
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/music/**","/headImg/**","/album/**","/singer/**","/songs/**","/songListImg/**")
                .excludePathPatterns("/","/login","/adminLogin","/register","/isLogin","/admin/**","/user/**","/songList/**","/song/**")
                .excludePathPatterns("/songList/fenLei","/song/all","/albums/**","/play")
                .excludePathPatterns("/albumDetail","/songListDetail","/singerDetail")
                .excludePathPatterns("/rankingList","/musics","/singers","/songs","/songLists","/albums")
                .excludePathPatterns("/doc.html","/webjars/**","/swagger-resources","/v2/api-docs");
    }

    /***
     * @MethodName: addResourceHandlers
     * @description: 静态资源映射
     * @Author: LiuTao
     * @Param: [registry]
     * @UpdateTime: 2023/3/9 19:57
     * @Return: void
     * @Throw:
     **/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/music/**").addResourceLocations("classpath:/static/music/");
//        开发环境
        registry.addResourceHandler("/album/**").addResourceLocations("classpath:/static/upload/album/");
        registry.addResourceHandler("/headImg/**").addResourceLocations("classpath:/static/upload/headImg/");
        registry.addResourceHandler("/singer/**").addResourceLocations("classpath:/static/upload/singer/");
        registry.addResourceHandler("/songListImg/**").addResourceLocations("classpath:/static/upload/songListImg/");
        registry.addResourceHandler("/songs/**").addResourceLocations("classpath:/static/upload/songs/");
//        生产环境
//        registry.addResourceHandler("/album/**").addResourceLocations("/upload/album/");
//        registry.addResourceHandler("/headImg/**").addResourceLocations("/upload/headImg/");
//        registry.addResourceHandler("/singer/**").addResourceLocations("/upload/singer/");
//        registry.addResourceHandler("/songListImg/**").addResourceLocations("/upload/songListImg/");
//        registry.addResourceHandler("/songs/**").addResourceLocations("/upload/songs/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /***
     * @MethodName: addViewControllers
     * @description: 视图控制器
     * @Author: LiuTao
     * @Param: [registry]
     * @UpdateTime: 2023/3/9 20:04
     * @Return: void
     * @Throw:
     **/
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addViewController("/").setViewName("client/index");
        registry.addViewController("/index").setViewName("client/iframe/index");
        registry.addViewController("/user").setViewName("client/user/home");
        registry.addViewController("/upPwd").setViewName("client/user/upPwd");
        registry.addViewController("/login").setViewName("client/user/login");
        registry.addViewController("/register").setViewName("client/user/register");
        registry.addViewController("/musics").setViewName("client/song/songs");
        registry.addViewController("/songLists").setViewName("client/songList/songlists");
        registry.addViewController("/singers").setViewName("client/singer/singers");
        registry.addViewController("/albums").setViewName("client/album/albums");
        registry.addViewController("/albumDetail").setViewName("client/album/album");
        registry.addViewController("/singerDetail").setViewName("client/singer/singer");
        registry.addViewController("/songListDetail").setViewName("client/songList/songlist");
        registry.addViewController("/login").setViewName("client/user/login");
        registry.addViewController("/play").setViewName("client/play/player");
        registry.addViewController("/rankingList").setViewName("client/rankingList/rankingLists");
        registry.addViewController("/iframe/musics").setViewName("client/iframe/song/songs");
        registry.addViewController("/iframe/songLists").setViewName("client/iframe/songList/songlists");
        registry.addViewController("/iframe/singers").setViewName("client/iframe/singer/singers");
        registry.addViewController("/iframe/albums").setViewName("client/iframe/album/albums");
        registry.addViewController("/admin").setViewName("admin/adminLogin");
        registry.addViewController("/admin/index").setViewName("admin/index");
        registry.addViewController("/admin/users").setViewName("admin/bootstrap-table/users");
        registry.addViewController("/admin/songs").setViewName("admin/bootstrap-table/songs");
        registry.addViewController("/admin/songLists").setViewName("admin/bootstrap-table/songLists");
        registry.addViewController("/admin/songListsData").setViewName("admin/bootstrap-table/songListsData");
        registry.addViewController("/admin/singers").setViewName("admin/bootstrap-table/singers");
        registry.addViewController("/admin/albums").setViewName("admin/bootstrap-table/albums");
        registry.addViewController("/user/index").setViewName("client/user/index");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(stringToDateFormatter);
        WebMvcConfigurer.super.addFormatters(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        WebMvcConfigurer.super.configureViewResolvers(registry);
    }
}