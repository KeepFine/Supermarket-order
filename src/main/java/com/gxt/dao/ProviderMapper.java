package com.gxt.dao;



import com.gxt.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.util.List;

public interface ProviderMapper {

	int add(Provider provider);

	List<Provider> getProviderList(@Param("proName") String proName, @Param("proCode") String proCode);

	int deleteProviderById(String delId);

	Provider getProviderById(@Param("id") String id);

	int modify(Provider provider);

}
