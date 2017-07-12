package com.brush.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.brush.dao.IRoleDao;
import com.brush.pojo.Role;
import com.brush.service.IRoleService;

/**
* 角色服务的实现类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午11:20:15
*/
@Service("roleService")  
public class RoleServiceImpl implements IRoleService{
	
	@Resource  
    private IRoleDao roleDao;  

	public Role getRoleById(int id) {
		return roleDao.getRoleById(id);
	}

	public List<Role> getAllRoleList() {
		return roleDao.getAllRoleList();
	}
}
