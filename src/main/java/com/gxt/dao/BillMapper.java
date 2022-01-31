package com.gxt.dao;


import com.gxt.pojo.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Connection;
import java.util.List;
@Repository
public interface BillMapper {

	int add(Bill bill);


	List<Bill> getBillList(Bill bill);


	int deleteBillById(@Param("delId") String delId);
	
	

 	Bill getBillById(@Param("id") String id);
	

 	int modify(Bill bill);


 	int getBillCountByProviderId(String providerId);

}
