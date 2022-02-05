package com.gxt.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxt.pojo.Bill;
import com.gxt.pojo.Provider;
import com.gxt.pojo.User;
import com.gxt.service.BillService;
import com.gxt.service.ProviderService;
import com.gxt.service.UserService;
import com.mysql.jdbc.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/Bill")
public class BillController {

    @Autowired
    @Qualifier("BillServiceImpl")
    private BillService billService;

    @Autowired
    @Qualifier("ProviderServiceImpl")
    private ProviderService providerService;

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping("/toQueryBill")
    public String toQueryBill(Model model){
        List<Provider> providerList = providerService.getProviderList(null,null);
        model.addAttribute("providerList",providerList);
        Bill bill = new Bill();
        List<Bill> billList = billService.getBillList(bill);
        model.addAttribute("billList",billList);
        return "billlist";
    }

    @RequestMapping("/query")
    public String query(Model model,String queryProductName,int queryProviderId,int queryIsPayment){
        if (queryProductName.equals("")){
            queryProductName = null;
        }

        List<Provider> providerList = providerService.getProviderList(null,null);
        model.addAttribute("providerList",providerList);
        Bill bill = new Bill();
        bill.setProductName(queryProductName);
        bill.setProviderId(queryProviderId);
        bill.setIsPayment(queryIsPayment);
        List<Bill> billList = billService.getBillList(bill);
        model.addAttribute("billList",billList);
        model.addAttribute("queryProductName",queryProductName);
        model.addAttribute("queryProviderId",queryProviderId);
        model.addAttribute("queryIsPayment",queryIsPayment);
        return "billlist";
    }

    @RequestMapping("/toBillAdd")
    public String toBillAdd(){
        return "billadd";
    }


    @RequestMapping("/getProviderlist")
    @ResponseBody
    public String getProviderlist() throws JsonProcessingException {
        List<Provider> getProviderlist = providerService.getProviderList(null,null);
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(getProviderlist);
        return str;
    }

    @RequestMapping("/add")
    public String add(Bill bill, HttpSession session){
        bill.setProductCount(new BigDecimal(String.valueOf(bill.getProductCount())).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setTotalPrice(new BigDecimal(String.valueOf(bill.getTotalPrice())).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setCreatedBy(((User)session.getAttribute("userSession")).getId());
        bill.setCreationDate(new Date());
        if (billService.add(bill)){
            return "redirect:toBillAdd";
        }else {
            return "redirect:toQueryBill";
        }
    }


    @RequestMapping("/delbill/{id}")
    public String delbill(Model model,@PathVariable("id") String id){
        billService.deleteBillById(id);
        return "redirect:/Bill/toQueryBill";
    }

    @RequestMapping("/view/{id}")
    public String view(Model model,@PathVariable("id") String id){
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill",bill);
        return "billview";
    }

    @RequestMapping("/modify/{id}")
    public String modify(Model model ,@PathVariable("id")String id){
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill",bill);
        return "billmodify";
    }
    @RequestMapping("/modifysave")
    public String modifysave(Bill bill){
        bill.setModifyDate(new Date());
        billService.modify(bill);
        return "redirect:/Bill/toQueryBill";
    }

    @ResponseBody
    @RequestMapping("/del")
    public String del(Model model,String method,String billid) throws JsonProcessingException {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(billid)){
            boolean flag = billService.deleteBillById(billid);
            if(flag){//删除成功
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
}
