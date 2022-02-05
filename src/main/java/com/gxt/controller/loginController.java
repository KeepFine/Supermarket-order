package com.gxt.controller;

import com.gxt.pojo.User;
import com.gxt.service.BillService;
import com.gxt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")

public class loginController  {
    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping("/in")
    public String login(HttpSession session,Model model, String userCode, String userPassword){
        User user = userService.login(userCode,userPassword);
        if(user!=null){
            session.setAttribute("userSession",user);
            return "frame";
        }else{
            model.addAttribute("error","用户名或密码不正确");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public  String logout(HttpSession session){
        session.removeAttribute("userSession");
        return "login";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}


