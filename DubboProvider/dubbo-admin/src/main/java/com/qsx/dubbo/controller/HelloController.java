package com.qsx.dubbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */
@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping(value = "/sayHi")
    public String sayHello(){
        return "你好！";
    }

    @RequestMapping(value = "/testHttpRequset", method = RequestMethod.POST)
    @ResponseBody
    public Object testHttpRequset(HttpServletRequest request, HttpServletResponse response, @RequestBody Map params)throws InterruptedException {
        System.out.println(params);
        params.put("Set-Cookie","");
        return params;
    }
}
