package com.gxt.service;

import com.gxt.dao.RoleMapper;
import com.gxt.pojo.Role;

import java.util.List;

public class RoleServiceImpl implements RoleService{

    private RoleMapper roleMapper;

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.getRoleList();
    }
}
