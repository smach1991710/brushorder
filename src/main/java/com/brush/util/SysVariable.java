package com.brush.util;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
* 配置一些系统变量
* @author 作者 songhao
* @version 创建时间：2017年6月27日 上午10:05:46
*/
public class SysVariable {
	
	private static Logger logger = Logger.getLogger(SysVariable.class);
	
	//上传路径
	public static String upload = "";
	
	//下载路径
	public static String download = "";
	
	//设置excel最多写多少行
	public static int excel_max_line = 50000;
	
	static{
		 Properties props=System.getProperties(); //获得系统属性集    
		 String osName = props.getProperty("os.name"); //操作系统名称   
         if(!osName.toLowerCase().contains("windows")){
        	 defaultLinuxConfig();
         }else{
        	 defaultWinConfig();
         }
	}
	
	/**
	 * 启用默认配置
	 */
	private static void defaultLinuxConfig() {
		upload = "/home/brushorder/upload/";
		download = "/home/brushorder/download/";
		excel_max_line = 50000;
	}

	/**
	 * 启用默认配置
	 */
	private static void defaultWinConfig() {
		upload = "D:/brushorder/upload/";
		download = "D:/brushorder/download/";
		excel_max_line = 50000;
	}
	
	public static void main(String[] args) {
		 Properties props=System.getProperties(); //获得系统属性集    
         String osName = props.getProperty("os.name"); //操作系统名称   
         System.out.println(osName);
         
         
	}
}


