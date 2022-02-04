package com.gxt.dao;



import com.gxt.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.util.List;

public interface UserMapper {

	int add(User user);

	User getLoginUser(String userCode);

	List<User> getUserList(@Param("userName") String userName, @Param("userRole") int userRole, @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);

	int getUserCount(@Param("userName") String userName, @Param("userRole") int userRole);

	int deleteUserById(String delId);

	User getUserById(String id) ;

	int modify(User user) ;

	int updatePwd(int id, String pwd) ;
	
	
}
