package com.edu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class TestController {

    @RequestMapping("/mytest1")
    public String testMethod(Date date){
        System.out.println(date);
        return null;
    }
}
