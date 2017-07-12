package com.brush.util;

/**
 * 国家,枚举类
 * @author zhengxin
 */
public enum Country {
	
	US("美国", "https://www.amazon.com", "en-US"),
	CA("加拿大", "https://www.amazon.ca", "en-CA"),
	UK("英国", "https://www.amazon.co.uk", "en-GB"),
	DE("德国", "https://www.amazon.de", "de-DE"),
	FR("法国", "https://www.amazon.fr", "fr-FR"),
	IT("意大利", "https://www.amazon.it", "it-IT"),
	JP("日本", "https://www.amazon.co.jp", "ja-JP"),
	ES("西班牙", "https://www.amazon.es", "es-ES"),
	AU("澳大利亚", "https://www.amazon.com.au", "en-AU"),
	BR("巴西", "https://www.amazon.com.br", "pt-BR"),
	CN("中国", "https://www.amazon.cn", "zh-CN"),
	IN("印度", "https://www.amazon.in", "en-IN"),
	MX("墨西哥", "https://www.amazon.com.mx", "es-MX"),
	NL("荷兰", "https://www.amazon.nl", "nl-NL");
	
	String chineseName;
	
	String amazonUrl;
	
	String contentLanguage;
	
	private Country(String chineseName, String amazonUrl, String contentLanguage){
		this.chineseName = chineseName;
		this.amazonUrl = amazonUrl;
		this.contentLanguage = contentLanguage;
	}

	public String getChineseName() {
		return chineseName;
	}

	public String getAmazonUrl() {
		return amazonUrl;
	}

	public String getContentLanguage() {
		return contentLanguage;
	}
}
