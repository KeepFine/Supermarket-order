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
        return false;
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
        return null;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        return 0;
    }

    @Override
    public User selectUserCodeExist(String userCode) {
        return null;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        return false;
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public boolean modify(User user) {
        return false;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        return false;
    }
}