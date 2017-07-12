package com.brush.pojo;

import java.util.Date;

/**
* 账号实体类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午10:06:31
*/
public class Account {
	
	private int id;
	private String email;//邮件地址
	private String pwd;//账号密码
	private String buyerId;//亚马逊Id
	private String countrycode;//国家编码
	private String status;//状态信息
	private int sysid;//系统id
	private String type;//账号类型
	private String ip;//ip地址
	private String creditcard;//信用卡信息
	private String postcode;
	private Date buydate;//购买日期
	private Date createdate;//创建日期
	private float accountmoney;//账户余额
	private String vpn;
	
	public Account(){}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getSysid() {
		return sysid;
	}
	public void setSysid(int sysid) {
		this.sysid = sysid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getBuydate() {
		return buydate;
	}
	public void setBuydate(Date buydate) {
		this.buydate = buydate;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public float getAccountmoney() {
		return accountmoney;
	}
	public void setAccountmoney(float accountmoney) {
		this.accountmoney = accountmoney;
	}
	public String getVpn() {
		return vpn;
	}
	public void setVpn(String vpn) {
		this.vpn = vpn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
