package com.gxt.controller;


import com.gxt.pojo.Role;
import com.gxt.pojo.User;
import com.gxt.service.RoleService;
import com.gxt.service.UserService;
import com.gxt.tools.Constants;
import com.gxt.tools.PageSupport;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("User")
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("RoleServiceImpl")
    private RoleService roleService;

    @RequestMapping("/query")
    public String query(Model model,String queryUserName,String queryUserRole,String pageIndex){
        System.out.println(queryUserName);
        if(StringUtils.isNullOrEmpty(queryUserName)){
            queryUserName = null;
        }
        if(StringUtils.isNullOrEmpty(queryUserRole)){
            queryUserRole = null;
        }
        if(StringUtils.isNullOrEmpty(pageIndex)){
            pageIndex = null;
        }
        int queryUserRole2 = 0;
        List<User> userList = null;

        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        if(StringUtils.isNullOrEmpty(queryUserName)){
            queryUserName = null;
        }
        if(queryUserRole != null && !queryUserRole.equals("")){
            queryUserRole2 = Integer.parseInt(queryUserRole);
        }

        if(pageIndex != null){

            currentPageNo = Integer.valueOf(pageIndex);
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole2);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        int currentPageNo2 = (currentPageNo-1)*pageSize;

//        currentPageNo = (currentPageNo-1)*pageSize;
        userList = userService.getUserList(queryUserName,queryUserRole2,currentPageNo2,pageSize);
        model.addAttribute("userList",userList);
        System.out.println(userList);
        List<Role> roleList = null;
        roleList = roleService.getRoleList();

        model.addAttribute("roleList",roleList);
        model.addAttribute("queryUserName",queryUserName);
        model.addAttribute("queryUserRole",queryUserRole2);
        model.addAttribute("totalPageCount",totalPageCount);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("currentPageNo",currentPageNo);

        return "userlist";
    }
}
