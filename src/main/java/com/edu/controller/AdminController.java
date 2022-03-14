package com.edu.controller;

import com.edu.bean.Admin;
import com.edu.bean.User;
import com.edu.service.AdminService;
import com.edu.service.AlbumService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    //管理员登录
    @RequestMapping("/adminLogin")
    @ResponseBody
    public Admin userLogin(Admin admin){
        Map<String,Object> map = new HashMap<String, Object>();
        System.out.println(admin);
        Admin login = adminService.login(admin);
        System.out.println(login);
        if(login ==null){
            map.put("result","登录失败");
        }else{
            map.put("result","登录成功");
            map.put("user",login);
        }
        return login;
    }
}
