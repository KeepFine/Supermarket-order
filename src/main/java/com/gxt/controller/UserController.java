package com.gxt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxt.pojo.Role;
import com.gxt.pojo.User;
import com.gxt.service.RoleService;
import com.gxt.service.UserService;
import com.gxt.service.UserServiceImpl;
import com.gxt.tools.Constants;
import com.gxt.tools.PageSupport;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/User")
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

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "useradd";
    }

    @ResponseBody
    @RequestMapping("/getrolelist")
    public String getrolelist(Model model,String method) throws JsonProcessingException {
        List<Role> userRole = roleService.getRoleList();
        model.addAttribute("userRole",userRole);
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(userRole);
        return str;
    }

    @RequestMapping("/addUser")
    public String addUser(HttpSession session,User user,String Tobirthday) throws ParseException {

        System.out.println(user);
        System.out.println(Tobirthday);
        user.setCreationDate(new Date());
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(Tobirthday));
        if (userService.add(user)){
            return "redirect:/User/query";
        }else {
            return "error";
        }
    }

    @ResponseBody
    @RequestMapping("/userCodeExist")
    public String userCodeExist(String userCode,String method) throws JsonProcessingException {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            User user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(resultMap);
        return str;
    }

    @ResponseBody
    @RequestMapping("/del")
    public String del(Model model,String method,String uid) throws JsonProcessingException {

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(uid)){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.deleteUserById(uid)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(resultMap);
        return str;
    }

    @RequestMapping("/view/{uid}")
    public String view(Model model,String method,@PathVariable("uid") String uid){
        User user = userService.getUserById(uid);
        model.addAttribute("user",user);
        return "userview";
    }

    @RequestMapping("/modify/{uid}")
    public String modify(Model model,String method,@PathVariable("uid") String uid){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        User user = userService.getUserById(uid);
        String birthday = simpleDateFormat.format(user.getBirthday());
        model.addAttribute("user",user);
        model.addAttribute("birthday",birthday);
        return "usermodify";
    }

    @RequestMapping("/modifySave")
    public String modifySave(HttpSession session,User user,String Tobirthday) throws ParseException {
        user.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(Tobirthday));
        if(userService.modify(user)){
            return "redirect:/User/query";
        }else {
            return "error";
        }
    }

    @RequestMapping("/toPwdmodify")
    public String toPwdmodify(){
        return "pwdmodify";
    }

    @ResponseBody
    @RequestMapping("/pwdmodify")
    public String pwdmodify(HttpSession session,Model model,String method,String oldpassword) throws JsonProcessingException {
        Object o = session.getAttribute(Constants.USER_SESSION);
        Map<String, String> resultMap = new HashMap<String, String>();
        if(null == o ){//session过期
            resultMap.put("result", "sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
            resultMap.put("result", "error");
        }else{
            String sessionPwd = ((User)o).getUserPassword();
            if(oldpassword.equals(sessionPwd)){
                resultMap.put("result", "true");
            }else{//旧密码输入不正确
                resultMap.put("result", "false");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(resultMap);
        return str;
    }

    @RequestMapping("/savepwd")
    public String savepwd(HttpSession session,Model model,String newpassword){
        Object o = session.getAttribute(Constants.USER_SESSION);
        boolean flag = false;
        if(o != null && !StringUtils.isNullOrEmpty(newpassword)){
            flag = userService.updatePwd(((User)o).getId(),newpassword);
            if(flag){
                model.addAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                session.removeAttribute(Constants.USER_SESSION);//session注销
            }else{
                model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        }else{
            model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        return "pwdmodify";
    }
}
