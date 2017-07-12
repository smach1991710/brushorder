package com.brush.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.brush.pojo.LoginUser;
import com.brush.pojo.User;

/**
 * 工具类
 * @author songhao
 */
public class Utils {
	
	private static Logger logger = Logger.getLogger(Utils.class);
	
	//全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    public static String format_ymd = "yyyy-MM-dd";
    public static String format_ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static String format_ymdhms2 = "yyyyMMddHHmmss";
    public static String format_ymd2 = "yyyyMMdd";
    public static String format_ymdh = "yyyy-MM-dd HH";
    
    /**
     * 日志打印
     * @param session
     * @param logger
     * @param user
     * @param info
     */
	public static void logger(HttpSession session,Logger logger,User user,String info){
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
		if(user == null){
			if(loginUser != null){
				user = loginUser.getUser();
			}
		}
		//logger.info("用户[" + (user == null ? "未知用户":user.getUser_name()) + "]," + info);
	}
    
    /**
     * 判断是不是一天的最后一个小时
     * @return
     */
    public static boolean isLastHour(String date){
    	String indexStr = " ";
    	int index = date.indexOf(indexStr);
    	boolean isLastHour = false;
    	if(index > -1){
    		String hour = date.substring(index + indexStr.length(), date.length());
    		System.out.println(hour);
    		if(hour.equals("23")){
    			isLastHour = true;
    		}
    	}
    	return isLastHour;
    }
    
    /**
     * 判断是否是一个数字
     * @param word
     * @return
     */
    public static boolean isDigital(String word){
    	for (int i = 0; i < word.length(); i++){
    		if (!Character.isDigit(word.charAt(i))){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * 获取项目的根路径
     * @return
     */
    public static String getWebPath(){
    	File f = new File(Utils.class.getResource("/").getPath());
    	String path = f.getParentFile().getParent();
    	return path;
    }
    
	/**
	 * 判断一个字符串是否为空,如果返回false表示不为空,返回true表示为空
	 * @param text
	 * @return
	 */
	public static boolean isNullStr(String text){
		if(text == null || "".equals(text.trim()) || "null".equals(text.trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据秒数返回时间格式
	 * @param sec
	 * @return
	 */
	public static String getFormatDateBySec(long msec){
		DateFormat df = new SimpleDateFormat(format_ymdhms);
		Date d = new Date();
		if(msec != 0){
			d = new Date(msec);
		}
		return df.format(d);
	}
	
	/**
	 * 通过md5加密
	 * @param strObj
	 * @return
	 */
	public static String GetMD5Code(String strObj) throws Exception {
		String resultString = null;
	    try {
	    	resultString = new String(strObj);
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (Exception ex) {
        	logger.error("获取md5值失败了");
        	throw ex;
	    }
	    return resultString;
	}
	
	/**
	 * 返回正常形式的日期字符串
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		DateFormat df = null;
		try{
			df = new SimpleDateFormat(format); 
			return df.format(date);
		}catch(Exception e){
			logger.error("返回日期格式字符串失败了");
		}
		return format;
	}
	
	/**
	 * 根据字符串和指定格式得到Date类型
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date getDateByFormat(String dateStr,String format){
		DateFormat df = null;
		try{
			df = new SimpleDateFormat(format); 
			return df.parse(dateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	// 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }
    
    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    
    /**
	 * 得到hfds上面的路径信息
	 * @param fullpath
	 * @param rootpath
	 * @return
	 */
	public static String getHdfsUrl(String fullpath,String rootpath){
		int index = fullpath.indexOf(rootpath);
		String hdfsUrl = fullpath;
		if(index != -1){
			hdfsUrl = fullpath.substring(index + rootpath.length(),fullpath.length());
		}
		return hdfsUrl;
	}
	
	/**
	 * 写集合中的文件到本地来
	 * @param path
	 * @param resList
	 * @return
	 */
	public static boolean writeFile(String path, List<String> resList) {
		boolean isWrited = false;
		BufferedWriter bw = null;
		try{
			 bw = new BufferedWriter(new OutputStreamWriter(  
		                new FileOutputStream(new File(path)), "utf-8"));  
			 int lineIndex = 0;
			 for(String line : resList){
				 lineIndex ++;
				 bw.write(line);
				 bw.newLine();
				 if(lineIndex % 1000 == 0){
					 bw.flush();
				 }
			 }
			 isWrited = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return isWrited;
	}
	
	/**
     * 以添加方式向文件中追加内容
     * @param path
     * @param contentList
     */
    public static void appendToFile(String path, List<String> contentList) throws Exception {
    	File file = new File(path);  
        if(file.createNewFile()){  
            logger.info("创建文件[" + file.getAbsolutePath() + "]成功");
        } 
    	
        BufferedWriter out = null;     
        try {     
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));     
            if(contentList != null && contentList.size() > 0){
            	int lineCount = 0;
            	for(String  content : contentList){
            		out.write(content);     
                    out.newLine();
                    lineCount++;
                    if(lineCount % 5000 == 0){
                    	out.flush();
                    }
            	}
            }
        } catch (Exception e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(out != null){  
                	out.flush();
                    out.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }     
    }  
	
	/**
	 * 重新写文件到目的路径
	 * @param file
	 * @param destpath
	 */
	public static void uploadFile(MultipartFile file, String destpath) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(file.getInputStream(),"utf-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destpath),"utf-8"));
			String tempLine = null;
			while((tempLine = br.readLine()) != null){
				bw.write(tempLine);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bw != null){
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
    public static void main(String[] args) throws Exception {
    	
    	//System.out.println("是否是数字:" + Utils.isDigital("宋浩"));
    	
    	System.out.println(isLastHour("2017-05-06 23"));
    	
    	System.out.println(GetMD5Code("123456"));
    	
    	System.out.println(getDateStr(new Date(),format_ymd));
    	
        //System.out.println(GetMD5Code("Aofei12345"));
    	//System.out.println(getDateStr(new Date()));
    	
    	/*String table = "tbllog_login";
    	System.out.println(getDateStr(new Date(), Utils.format_ymd));*/
        
        //System.out.println(System.currentTimeMillis() / 1000);
    	
    	//System.out.println(getFormatDateBySec(1472030845000l));
    }
}
