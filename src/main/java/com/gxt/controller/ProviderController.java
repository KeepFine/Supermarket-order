package com.gxt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxt.pojo.Provider;
import com.gxt.pojo.User;
import com.gxt.service.UserService;
import com.gxt.service.BillService;
import com.gxt.service.ProviderService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/Provider")
public class ProviderController {

    @Autowired
    @Qualifier("BillServiceImpl")
    private BillService billService;

    @Autowired
    @Qualifier("ProviderServiceImpl")
    private ProviderService providerService;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService UserService;

    @RequestMapping("/query")
    public String query(Model model,String queryProName,String queryProCode){
        if(StringUtils.isNullOrEmpty(queryProName)){
            queryProName = null;
        }
        if(StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = null;
        }
        List<Provider> providerList = providerService.getProviderList(queryProName,queryProCode);
        model.addAttribute("providerList",providerList);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("queryProCode",queryProCode);
        return "providerlist";
    }

    @RequestMapping("/view/{proid}")
    public String view(Model model,@PathVariable("proid")String proid){
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute("provider",provider);
        return "providerview";
    }

    @RequestMapping("/modify/{proid}")
    public String modify(Model model,@PathVariable("proid")String proid){
        Provider provider = providerService.getProviderById(proid);
        model.addAttribute("provider",provider);
        return "providermodify";
    }


    @RequestMapping("/modifySave")
    public String modifySave(HttpSession session,Provider provider){
        provider.setModifyBy(((User)session.getAttribute("userSession")).getId());
        provider.setModifyDate(new Date());
        if(providerService.modify(provider)){
            return "redirect:/Provider/query";
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping("/delprovider")
    public String delprovider(Model model,String method,String proid) throws JsonProcessingException {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(proid)){
            int flag = providerService.deleteProviderById(proid);
            if(flag!=0){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(resultMap);
        return str;
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "provideradd";
    }

    @RequestMapping("/addProvider")
    public String addProvider(HttpSession session,Provider provider){
        provider.setCreatedBy(((User)session.getAttribute("userSession")).getId());
        provider.setCreationDate(new Date());
        if(providerService.add(provider)){
            return "redirect:/Provider/query";
        }
        return "error";
    }
}
