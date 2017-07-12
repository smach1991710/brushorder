package com.brush.service;

import java.util.List;

import com.brush.pojo.Role;

/**
* 角色服务类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午11:18:43
*/
public interface IRoleService {
	
	/**
	 * 根据id获取角色实体
	 * @param id
	 * @return
	 */
	public Role getRoleById(int id);
	
	
	/**
	 * 获取所有的角色列表
	 * @return
	 */
	public List<Role> getAllRoleList();
}
