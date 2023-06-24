package net.ltbk.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Program: music
 * @ClassName SystemController
 * @Author: liutao
 * @Description: 系统接口
 * @Create: 2023-04-04 22:01
 * @Version 1.0
 **/

@Controller
public class SystemController {
    @GetMapping("/")
    String index() {
        return "client/index";
    }
}
