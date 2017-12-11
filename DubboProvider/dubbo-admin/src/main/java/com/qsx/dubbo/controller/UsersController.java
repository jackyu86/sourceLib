package com.qsx.dubbo.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.qsx.dubbo.model.Users;
import com.qsx.dubbo.service.UsersService;
import com.qsx.dubbo.service.impl.UsersServiceImpl;
import com.qsx.dubbo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qiu on 17/4/24.
 */
@Controller
@RequestMapping("/UsersController")
public class UsersController {
    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public Object getUsers(){
        return usersService.getUsers();
    }

    @RequestMapping(value = "/getUsersByName", method = RequestMethod.POST)
    @ResponseBody
    public Object getUsersByName(@RequestBody Map map){
        String name = map.get("name").toString();
        List list = new ArrayList();
        try {
            list = usersService.getUsersByName(name);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.fail("1","查询用户失败");
        }
        return JsonUtil.success(list);
    }

    @RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    @ResponseBody
    public Object addUsers(@RequestBody Map map){

        String name = map.get("name").toString();
        String sex = map.get("sex").toString();
        int age = Integer.parseInt(map.get("age").toString());
        Users u = new Users();
        u.setName(name);
        u.setSex(sex);
        u.setAge(age);
        try {
            usersService.addUsers(u);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.fail("1","更新用户失败");
        }
        return JsonUtil.success(null);
    }

    @RequestMapping(value = "/updateUsers", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUsers(@RequestBody Map map){
        String name = map.get("name").toString();
        String sex = map.get("sex").toString();
        int age = Integer.parseInt(map.get("age").toString());
        int id = Integer.parseInt(map.get("id").toString());
        Users u = new Users();
        u.setName(name);
        u.setSex(sex);
        u.setAge(age);
        u.setId(id);
        try {
            usersService.updateUsers(u);
        }catch (Exception e){
            return JsonUtil.fail("1","更新用户失败");
        }
        return JsonUtil.success(null);
    }

    @RequestMapping(value = "/deleteUsers", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteUsersJsonUtil(@RequestBody Map map){
        int id = Integer.parseInt(map.get("id").toString());
        try {
            usersService.deleteUsers(id);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.fail("1","删除用户失败");
        }
        return JsonUtil.success(null);
    }

}
