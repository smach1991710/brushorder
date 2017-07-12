package com.brush.util;

/**
* 类说明
* @author 作者 songhao
* @version 创建时间：2017年6月27日 下午2:55:47
*/
public class EnumUtils {
	
	/**
	 * 根据编码获取国家对象
	 * @param code
	 * @return
	 */
	public static Country getCountryByCode(String code){  
		for(Country country : Country.values()){  
			if(code.equals(country.getContentLanguage())){  
				return country;  
			}  
		}  
		return null;  
	} 
	
	/**
	 * 获取状态对象
	 * @param code
	 * @return
	 */
	public static BuyerStatus getStatusByCode(String code){  
		for(BuyerStatus status : BuyerStatus.values()){  
			if(code.equals(status.getValue())){  
				return status;  
			}  
		}  
		return null;  
	} 
	
	public static void main(String[] args) {
		System.out.println(getStatusByCode("yes").code);
	}
}
