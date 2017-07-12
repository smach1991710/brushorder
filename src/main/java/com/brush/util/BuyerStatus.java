package com.brush.util;

/**
 * 买家状态,枚举类
 * @author songhao
 */
public enum BuyerStatus {
	
	NOREG("未注册", "no"),
	NORMALLOGIN("正常登陆","yes"),
	PWDERROR("登陆-密码错误", "disable"),
	NOMONEY("留评-金额不足", "valid-amount"),
	NOALLOW("留评-不允许","un-accept"),
	ALLOWSTAY("可留评","review"),
	IMGVALIDATE("登陆-图片验证", "image-auth"),
	LOGINSAFEVALIDATE("登陆-安全性问题验证", "security-question"),
	LOGINIMGVALIDATE("登陆-机器图片验证", "image-robert"),
	LOGINEMAILVALIDATE("登陆-邮箱验证","code-verify");
	
	String code;
	String value;
	
	private BuyerStatus(String code, String value){
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}
