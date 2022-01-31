package com.gxt.service;



import com.gxt.pojo.User;

import java.util.List;

public interface UserService {

	boolean add(User user);

	User login(String userCode, String userPassword);

	List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);

	int getUserCount(String queryUserName, int queryUserRole);

	User selectUserCodeExist(String userCode);

	boolean deleteUserById(Integer delId);

	User getUserById(String id);

	boolean modify(User user);

	boolean updatePwd(int id, String pwd);
}
