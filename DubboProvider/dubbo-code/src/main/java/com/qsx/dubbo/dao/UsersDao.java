package com.qsx.dubbo.dao;

import com.qsx.dubbo.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qiu on 17/4/21.
 */
@Component("UsersDao")
public interface UsersDao {
    List<Users> getUsers();

    List<Users> getUsersByName(String name) throws Exception;

    void addUsers(Users users) throws Exception;
    void updateUsers(Users users) throws Exception;
    void deleteUsers(int id) throws Exception;
}
