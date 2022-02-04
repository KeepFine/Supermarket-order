package com.gxt.service;


import com.gxt.dao.BillMapper;
import com.gxt.dao.UserMapper;
import com.gxt.pojo.Bill;
import com.gxt.pojo.User;


import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService{

    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean add(User user) {
        if(userMapper.add(user)!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User login(String userCode, String userPassword) {
        User user = userMapper.getLoginUser(userCode);
        if(null!=user){
            if(!user.getUserPassword().equals(userPassword)){
                user = null;
            }
        }
        return user;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        return userMapper.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        return userMapper.getUserCount(queryUserName,queryUserRole);
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        User user = null;
        user = userMapper.getLoginUser(userCode);
        return user;
    }

    @Override
    public boolean deleteUserById(String delId) {
        if (userMapper.deleteUserById(delId)!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public boolean modify(User user) {
        if(userMapper.modify(user)!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        return false;
    }
}