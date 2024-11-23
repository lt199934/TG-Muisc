package net.ltbk.music.config;

import net.ltbk.music.common.interceptor.AdminInterceptor;
import net.ltbk.music.common.interceptor.LoginInterceptor;
import net.ltbk.music.formatter.StringToDateFormatter;
import org.jetbrains.annotations.NotNull;
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
    private static final String STATIC_DIR = "classpath:/static";
    /**
     * 文件存放的目录
     **/
    private static final String FILE_DIR = "/upload";
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
                .excludePathPatterns("/", "/login", "/adminLogin", "/register", "/isLogin", "/user/userLogin", "/songList/**", "/song/**")
                .excludePathPatterns("/songList/fenLei", "/song/all", "/albums/**", "/play", "/updatePlayCount/**")
                .excludePathPatterns("/albumDetail", "/songListDetail", "/singerDetail", "/user")
                .excludePathPatterns("/rankingList","/musics","/singers","/songs","/songLists","/albums")
                .excludePathPatterns("/doc.html","/webjars/**","/swagger-resources","/v2/api-docs");

        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/");
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
        registry.addResourceHandler("/music/**").addResourceLocations(STATIC_DIR + "/music/");
//        开发环境
        registry.addResourceHandler("/album/**").addResourceLocations(STATIC_DIR + FILE_DIR + "/album/");
        registry.addResourceHandler("/headImg/**").addResourceLocations(STATIC_DIR + FILE_DIR + "/headImg/");
        registry.addResourceHandler("/singer/**").addResourceLocations(STATIC_DIR + FILE_DIR + "/singer/");
        registry.addResourceHandler("/songListImg/**").addResourceLocations(STATIC_DIR + FILE_DIR + "/songListImg/");
        registry.addResourceHandler("/songs/**").addResourceLocations(STATIC_DIR + FILE_DIR + "/songs/");
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
    public void addViewControllers(@NotNull ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        // 客户端
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
        // 管理端
//        registry.addViewController("/admin").setViewName("admin/index");
//        registry.addViewController("/admin/users").setViewName("admin/bootstrap-table/users");
//        registry.addViewController("/admin/songs").setViewName("admin/bootstrap-table/songs");
//        registry.addViewController("/admin/songLists").setViewName("admin/bootstrap-table/songLists");
//        registry.addViewController("/admin/songListsData").setViewName("admin/bootstrap-table/songListsData");
//        registry.addViewController("/admin/singers").setViewName("admin/bootstrap-table/singers");
//        registry.addViewController("/admin/albums").setViewName("admin/bootstrap-table/albums");
//        registry.addViewController("/user/index").setViewName("client/user/index");
    }

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(stringToDateFormatter);
        WebMvcConfigurer.super.addFormatters(registry);
    }


    @Override
    public void configureMessageConverters(@NotNull List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Override
    public void configureViewResolvers(@NotNull ViewResolverRegistry registry) {
        WebMvcConfigurer.super.configureViewResolvers(registry);
    }
}
