package com.qsx.dubbo.service;

import com.qsx.dubbo.model.Users;

import java.util.List;

/**
 * Created by qiu on 17/4/24.
 */
public interface UsersService {
    List<Users> getUsers();

    List<Users> getUsersByName(String name) throws Exception;

    void addUsers(Users users) throws Exception;
    void updateUsers(Users users)throws Exception;
    void deleteUsers(int id)throws Exception;
}
