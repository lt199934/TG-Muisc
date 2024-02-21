package net.ltbk.music.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.ltbk.music.bean.Admin;
import net.ltbk.music.common.Result;
import net.ltbk.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: music
 * @ClassName SystemController
 * @Author: liutao
 * @Description: 系统接口
 * @Create: 2023-04-04 22:01
 * @Version 1.0
 **/
@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    private final Map<String, HttpSession> sessionMap = new HashMap<>();
    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<Integer> userLogin(Admin admin, HttpSession session) {
        log.info("登录用户：{}", admin);
        Admin login = userService.admin(admin);
        if (login == null || !admin.getPwd().equals(login.getPwd())) {
            return Result.error("用户名或密码错误");
        }
        // 存储会话
        if (!sessionMap.isEmpty()) {
            // 判断是否登录过
            if (sessionMap.containsKey(login.getSuperId().toString())) {
                // 过期上一个session
                sessionMap.get(login.getSuperId().toString()).invalidate();
            }
        }
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute("user", login);
        session.setAttribute(login.getSuperId().toString(), login);
        sessionMap.put(login.getSuperId().toString(), session);
        return Result.success("登录成功", login.getSuperId());
    }

    @GetMapping("/isLogin")
    public Boolean isLogin(String superId) {
        return sessionMap.containsKey(superId);
    }

    @PostMapping("/logout")
    public String isLogout(String superId, HttpSession session) {
        System.out.println("清除用户：" + superId);
        sessionMap.remove(superId);
        session.removeAttribute(superId);
        session.invalidate();
        return "/login";
    }


}
