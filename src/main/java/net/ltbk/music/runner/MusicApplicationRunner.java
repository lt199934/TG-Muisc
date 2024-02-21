package net.ltbk.music.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Program: TG-Music
 * @ClassName MusicApplicationRunner
 * @Author: liutao
 * @Description: 程序启动后通过ApplicationRunner处理一些事务
 * @Create: 2023-11-15 17:45
 * @Version 1.0
 **/
@Slf4j
@Component
public class MusicApplicationRunner implements ApplicationRunner {
    @Value("${server.port}")
    private int port;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("程序部署完成，访问地址：http://localhost:" + port);
        log.info("Swagger文档，访问地址：http://localhost:" + port + "/doc.html");
    }
}
