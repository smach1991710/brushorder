package com.brush.pojo;

/**
* 角色实体
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午10:17:13
*/
public class Role {
	
	private int id;//角色id
	private String rolename;//角色名称
	
	public Role(){}
	
	public Role(int id, String rolename) {
		super();
		this.id = id;
		this.rolename = rolename;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
