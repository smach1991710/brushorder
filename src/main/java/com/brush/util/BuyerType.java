package com.brush.util;

/**
 * 买家状态,枚举类
 * @author songhao
 */
public enum BuyerType {
	
	RANK("排名", "wishlist"),
	NEWRANK("新排名","rankws"),
	EMAIL("邮件", "sendemail"),
	FBA("FBA", "fba"),
	FBM("FBM","fbm");
	
	String code;
	String value;
	
	private BuyerType(String code, String value){
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
