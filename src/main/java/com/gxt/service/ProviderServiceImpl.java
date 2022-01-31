package com.gxt.service;



import com.gxt.dao.ProviderMapper;
import com.gxt.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public class ProviderServiceImpl implements ProviderService{

    private ProviderMapper providerMapper;

    public void setProviderMapper(ProviderMapper providerMapper) {
        this.providerMapper = providerMapper;
    }

    @Override
    public boolean add(Provider provider) {
        if(providerMapper.add(provider)!=0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Provider> getProviderList(String proName,String proCode) {
        List<Provider> list= new ArrayList<>();
        list = providerMapper.getProviderList(proName,proCode);
        return list;
    }

    @Override
    public int deleteProviderById(String delId) {
        return providerMapper.deleteProviderById(delId);
    }

    @Override
    public Provider getProviderById(String id) {
        return providerMapper.getProviderById(id);
    }

    @Override
    public boolean modify(Provider provider) {
        if(providerMapper.modify(provider)!=0){
            return true;
        }else {
            return false;
        }
    }
}