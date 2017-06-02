package com.qsx.dubbo.service.impl;

import com.qsx.dubbo.dao.UsersDao;
import com.qsx.dubbo.model.Users;
import com.qsx.dubbo.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiu on 17/4/24.
 */
//@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;
    @Override
    public List<Users> getUsers() {

        List<Users> list = new ArrayList<>();
        Users u = new Users();
        u.setAge(1);
        u.setId(1);
        u.setName("zhangsan");
        u.setSex("男");
        list.add(u);
        return usersDao.getUsers();
    }

    @Override
    public List<Users> getUsersByName(String name) throws Exception  {
        return usersDao.getUsersByName(name);
    }

    @Override
    public void addUsers(Users users) throws Exception{
        usersDao.addUsers(users);
    }

    @Override
    public void updateUsers(Users users) throws Exception{
        usersDao.updateUsers(users);
    }

    @Override
    public void deleteUsers(int id) throws Exception{
        usersDao.deleteUsers(id);
    }
}
