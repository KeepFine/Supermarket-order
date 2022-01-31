package com.gxt.service;


import com.gxt.dao.BillMapper;
import com.gxt.pojo.Bill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BillServiceImpl implements BillService{

    private BillMapper billMapper;

    public void setBillMapper(BillMapper billMapper) {
        this.billMapper = billMapper;
    }

    public boolean add(Bill bill) {
        if(billMapper.add(bill)!=0){
            return true;
        }else {
            return false;
        }
    }

    public List<Bill> getBillList(Bill bill) {
        return billMapper.getBillList(bill);
    }

    public boolean deleteBillById(String delId) {
        if(billMapper.deleteBillById(delId)!=0){
            return true;
        }else {
            return false;
        }
    }

    public Bill getBillById(String id) {
        return billMapper.getBillById(id);
    }

    public boolean modify(Bill bill) {
        if (billMapper.modify(bill) != 0) {
            return true;
        } else {
            return false;
        }

    }
}