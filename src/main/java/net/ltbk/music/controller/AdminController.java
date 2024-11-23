package net.ltbk.music.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Program: music
 * @ClassName SystemController
 * @Author: liutao
 * @Description: 管理系统接口
 * @Create: 2023-04-04 22:01
 * @Version 1.0
 **/
@Slf4j
@RequestMapping("/admin")
@Controller
public class AdminController {
    private static final String PREFIX = "admin";
    private static final String BOOTSTRAP_PREFIX = "admin/bootstrap-table";

    @GetMapping
    public String index() {
        return PREFIX + "/index";
    }

    @GetMapping("/users")
    public String users() {
        return BOOTSTRAP_PREFIX + "/users";
    }

    @GetMapping("/songs")
    public String songs() {
        return BOOTSTRAP_PREFIX + "/songs";
    }

    @GetMapping("/songLists")
    public String songLists() {
        return BOOTSTRAP_PREFIX + "/songLists";
    }

    @GetMapping("/songListsData")
    public String songListsData() {
        return BOOTSTRAP_PREFIX + "/songListsData";
    }

    @GetMapping("/singers")
    public String singers() {
        return BOOTSTRAP_PREFIX + "/singers";
    }

    @GetMapping("/albums")
    public String albums() {
        return BOOTSTRAP_PREFIX + "/albums";
    }


}
