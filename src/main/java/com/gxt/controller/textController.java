package com.gxt.controller;


import com.gxt.pojo.Bill;
import com.gxt.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/t1")
public class textController {

    @Autowired
    @Qualifier("BillServiceImpl")
    private BillService billService;

    @RequestMapping("/t1")
    public String text(Model model, Bill bill){
        String result = "";
        if(billService.add(bill)){
            System.out.println("ok");
                result = "ok";
        }else {
            System.out.println("false");
            result = "no";
        }
        model.addAttribute("msg",result);
        return "text";
    }
}
