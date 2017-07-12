package com.brush.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.brush.pojo.Role;

/**
* 角色操作的dao
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午10:54:28
*/
@Repository("roleDao")
public interface IRoleDao {

	Role getRoleById(int id);

	List<Role> getAllRoleList();

}


