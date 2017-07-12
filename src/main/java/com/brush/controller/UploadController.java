package com.brush.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brush.util.SysVariable;

/**
* 执行文件的上传下载的Controller
* @author 作者 songhao
* @version 创建时间：2017年6月27日 上午9:44:28
*/
@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private Logger logger = Logger.getLogger(UploadController.class);
	
	@RequestMapping(value="/downtemplate")
    public void downtemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        BufferedInputStream in = null;  
        BufferedOutputStream out = null;  
        request.setCharacterEncoding("UTF-8");  
        String filename = "BuyerAccountTemplate.xls";
        try {  
            File f = new File(SysVariable.download + "/template/" + filename);  
            response.reset();// 不加这一句的话会出现下载错误 
            response.setHeader("Content-disposition", "attachment;filename=" + filename);   // 设定输出文件头   
            response.setContentType("application/vnd.ms-excel");   // 定义输出类
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-Length",String.valueOf(f.length())); 
            in = new BufferedInputStream(new FileInputStream(f));  
            out = new BufferedOutputStream(response.getOutputStream());  
            byte[] data = new byte[1024];  
            int len = 0;  
            while (-1 != (len=in.read(data, 0, data.length))) {  
                out.write(data, 0, len);  
            }  
        } catch (Exception e) {  
        	logger.error("执行文件的下载报错了," + e.toString());
        } finally {  
            if (in != null) {  
                in.close();  
            }  
            if (out != null) {  
                out.close();  
            }  
        }  
    }  
}
