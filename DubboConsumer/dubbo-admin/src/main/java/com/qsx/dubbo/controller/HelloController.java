package com.qsx.dubbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/11/3.
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/sayHi")
    public String sayHello(){
        System.out.println("进来了");
        return "你好！";
    }


}
