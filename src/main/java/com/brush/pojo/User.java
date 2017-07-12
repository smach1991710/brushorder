package com.brush.pojo;

/**
* 用户实体信息，对应数据库t_user表
* @author 作者 songhao
* @version 创建时间：2017年6月19日 下午4:47:49
*/
public class User {
	
	private int id;//用户id
	private String name;//用户名
	private String pwd;//用户密码
	private String email;//用户email
	private String qq;//用户qq
	private int roleid;//角色id
	private float accoutmoney;//账户余额
	
	public User(){}

	public User(int id, String name, String pwd, String email, String qq, int roleid, float accoutmoney) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.qq = qq;
		this.roleid = roleid;
		this.accoutmoney = accoutmoney;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public float getAccoutmoney() {
		return accoutmoney;
	}
	public void setAccoutmoney(float accoutmoney) {
		this.accoutmoney = accoutmoney;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
