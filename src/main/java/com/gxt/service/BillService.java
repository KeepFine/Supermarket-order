package com.gxt.service;


import com.gxt.pojo.Bill;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
	public interface BillService {

 		boolean add(Bill bill);

 		List<Bill> getBillList(Bill bill);

 		boolean deleteBillById(String delId);

 		Bill getBillById(String id);

 		boolean modify(Bill bill);
	
}
