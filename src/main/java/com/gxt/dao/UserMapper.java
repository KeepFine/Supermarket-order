package com.gxt.dao;



import com.gxt.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserMapper {

	int add(User user);

	User getLoginUser(String userCode);

	List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);

	int getUserCount(String userName, int userRole);

	int deleteUserById(Integer delId);

	User getUserById(String id) ;

	int modify(User user) ;

	int updatePwd(int id, String pwd) ;
	
	
}
