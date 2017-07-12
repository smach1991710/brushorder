package com.brush.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.brush.pojo.Account;
import com.brush.pojo.LoginUser;
import com.brush.pojo.User;
import com.brush.service.IAccountService;
import com.brush.util.BuyerStatus;
import com.brush.util.BuyerType;
import com.brush.util.Country;
import com.brush.util.EnumUtils;
import com.brush.util.ExcelUtils;
import com.brush.util.SysVariable;
import com.brush.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* 账号控制类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午10:05:34
*/
@Controller
@RequestMapping("/account")
public class AccountController {
	
	private Logger logger = Logger.getLogger(AccountController.class);
	
	@Resource
	private IAccountService accountService;
	
	@RequestMapping(value="/accountmanager")
	public String usermanager(HttpServletRequest request,ModelMap model){
		//获取国家信息，填充下拉框
		request.setAttribute("countryenum", Country.values());
		request.setAttribute("statusenum", BuyerStatus.values());
		request.setAttribute("typeenum", BuyerType.values());
		return "account/accountmanager";
	} 
	
	@RequestMapping(value="/uploadUi")
	public String uploadUi(HttpServletRequest request,ModelMap model){
		return "account/uploadUi";
	} 
	
	@RequestMapping(value="/uploadAccounts")
	public String uploadAccounts(HttpSession session,
			ModelMap model,HttpServletRequest request,
			@RequestParam("files") MultipartFile file){
		//首先获取对应的用户信息
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
		User user = null;
		if(loginUser != null){
			user = loginUser.getUser();
		}
		//保存文件信息，并返回结果
		String path = saveFile(file,user);
		//判断文件信息是否是空，解析xls文件，并返回响应结果
		if(!Utils.isNullStr(path)){
			File excelfile = new File(path);
			if(excelfile.isFile()){
				//返回成功
				request.setAttribute("msg", true);
				//从第二行开始读取
				int startIndex = 2;
				List<List<String>> rowList = null;
				try {
					rowList = ExcelUtils.getListByExcelPath(path, startIndex);
					//设置成功添加的记录数
					int savesize = 0;
					for(List<String> row : rowList){
						Account account = new Account();
						int size = row.size();
						account.setCountrycode(size > 0 ? row.get(0) : "");
						account.setEmail(size > 1 ? row.get(1) : "");
						account.setPwd(size > 2 ? row.get(2) : "");
						account.setType(size > 3 ? row.get(3) : "");
						account.setStatus(size > 4 ? row.get(4) : "");
						account.setPostcode(size > 5 ? row.get(5) : "");
						account.setCountrycode(size > 7 ? row.get(7) : "");
						account.setBuydate(new Date());
						account.setCreatedate(new Date());
						//添加到数据中去
						boolean isAdded = accountService.saveAccount(account);
						if(isAdded){
							savesize++;
						}
					}
					request.setAttribute("successline", savesize);
				} catch (Exception e) {
					logger.error("读取excel出错了," + e.toString());
				}
			}
		}else{
			request.setAttribute("msg", false);
		}
		return "account/uploadUi";
	}
	
    /**
     * 保存最终的文件
     * @param file
     * @param user
     * @return
     */
    private String saveFile(MultipartFile file,User user) {
    	HSSFWorkbook workbook = null;
    	OutputStream os = null;
	    BufferedOutputStream bos = null;
	    //获取当前系统时间
  		String pathdate = Utils.getDateStr(new Date(), Utils.format_ymd2);
  		//得到上传的一段路径路名
  		String originalname = file.getOriginalFilename();
  		//判断源文件名称是否为空
  		if(Utils.isNullStr(originalname)){
  			originalname = originalname.replaceAll(" ", "");
  		}
        try {
        	//获取最终要保存的位置
			String destpath = SysVariable.upload + File.separator + pathdate + File.separator + (user == null ? "unknow" : user.getName());
			// 文件保存路径
			File partent = new File(destpath);
			if(!partent.exists()){
				partent.mkdirs();
			}
			destpath = destpath + File.separator + originalname;
        	workbook = new HSSFWorkbook(file.getInputStream());
            os = new FileOutputStream(new File(destpath));
            bos = new BufferedOutputStream(os);
            bos.flush();
            workbook.write(bos);
            return destpath;
        } catch (Exception e) {
        	logger.error("导出excel文件失败了," + e.toString());
        }finally{
        	try {
        		if(bos != null){
        			bos.close();
        		}
        		if(os != null){
        			os.close();
        		}
			} catch (IOException e) {
				 logger.error("关闭流文件失败了," + e.toString());
			}
        }
        return null;
    }
	
	/***
	 * 保存文件
	 * @param file
	 * @return
	 */
	/*private String saveFile(MultipartFile file,User user) {
		//获取当前系统时间
		String pathdate = Utils.getDateStr(new Date(), Utils.format_ymd2);
		//得到上传的一段路径路名
		String originalname = file.getOriginalFilename();
		//判断源文件名称是否为空
		if(Utils.isNullStr(originalname)){
			originalname = originalname.replaceAll(" ", "");
		}
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				//获取最终要保存的位置
				String destpath = SysVariable.upload + File.separator + pathdate + File.separator + (user == null ? "unknow" : user.getName());
				// 文件保存路径
				File partent = new File(destpath);
				if(!partent.exists()){
					partent.mkdirs();
				}
				destpath = destpath + File.separator + originalname;
				// 转存文件
				Utils.uploadFile(file,destpath);
				return destpath;
			} catch (Exception e) {
				logger.error("用户:[" + user == null ? "未知" : user.getName() + "]，保存文件报错了");
			}
		}
		return null;
	}*/
	
	/**
	 * 根据用户提交的请求进行下载
	 * @return
	 */
	@RequestMapping(value="/downaccountlist")
	public ResponseEntity<byte[]> downaccountlist(HttpSession session,
				HttpServletRequest request,
				HttpServletResponse response,
				@RequestParam(value = "countrycode",defaultValue="") String countrycode,
				@RequestParam(value = "status",defaultValue="") String status,
				@RequestParam(value = "email",defaultValue="") String email,
				@RequestParam(value = "sysid",defaultValue="0") int sysid,
				@RequestParam(value = "type",defaultValue="") String type,
				@RequestParam(value = "ip",defaultValue="") String ip,
				@RequestParam(value = "buyerId",defaultValue="") String buyerId,
				@RequestParam(value = "startbuydate",defaultValue="") String startbuydate,
				@RequestParam(value = "endbuydate",defaultValue="") String endbuydate,
				@RequestParam(value = "startcreatedate",defaultValue="") String startcreatedate,
				@RequestParam(value = "endcreatedate",defaultValue="") String endcreatedate){
		
		//设置要写的标题信息
		String[] titleArr = new String[]{
				"ID","账号/亚马逊BuyerID","密码","国家","账号类型","状态","信用卡信息","电话邮编",
				"账号余额","VPN(IP-User Pwd)","最后购买日期","创建日期"};
		//查询字段信息
		Map<String,Object> queryParams = getRequestParams(countrycode,status,email,sysid,
				type,ip,buyerId,startbuydate,endbuydate,startcreatedate,endcreatedate);
		
		List<Account> accountList = new ArrayList<Account>();
    	try{
    		//查询得到用户列表
    		accountList = accountService.queryAccount(queryParams);
     		download(response,titleArr,accountList);
    	}catch(Exception e){
    		logger.error("执行查询出错了," + e.getMessage());
    	}
    	return null;
	}
	
	/**
	 * 根据用户提交的请求字段进行查询
	 * @return
	 */
	@RequestMapping(value="/accountList",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> accountList(HttpSession session,
				HttpServletRequest request,
				@RequestParam(value = "countrycode",defaultValue="") String countrycode,
				@RequestParam(value = "status",defaultValue="") String status,
				@RequestParam(value = "email",defaultValue="") String email,
				@RequestParam(value = "sysid",defaultValue="0") int sysid,
				@RequestParam(value = "type",defaultValue="") String type,
				@RequestParam(value = "ip",defaultValue="") String ip,
				@RequestParam(value = "buyerId",defaultValue="") String buyerId,
				@RequestParam(value = "startbuydate",defaultValue="") String startbuydate,
				@RequestParam(value = "endbuydate",defaultValue="") String endbuydate,
				@RequestParam(value = "startcreatedate",defaultValue="") String startcreatedate,
				@RequestParam(value = "endcreatedate",defaultValue="") String endcreatedate,
				@RequestParam(value = "page",defaultValue="1") int page,
				@RequestParam(value = "size",defaultValue="20") int size){
		
		//获取查询请求参数信息
		Map<String,Object> queryParams = getRequestParams(countrycode,status,email,sysid,
				type,ip,buyerId,startbuydate,endbuydate,startcreatedate,endcreatedate);
		//设置from和to参数
    	int from = (page - 1) * size; 
    	queryParams.put("from", from);
    	queryParams.put("size",size);
    	//写结果给前端页面
		Map<String,Object> param = new HashMap<String,Object>();
        JSONArray jsonArray = new JSONArray();
        long total = 0;
    	List<Account> accountList = new ArrayList<Account>();
    	try{
    		//查询得到用户列表
    		accountList = accountService.queryAccount(queryParams);
    		//查询得到总的记录数
    		total = accountService.getAccountCount(queryParams);
    		for(Account account : accountList){
    			JSONObject jsonObject = new JSONObject();
    			Country country = EnumUtils.getCountryByCode(account.getCountrycode());
    			BuyerStatus buyerstatus = EnumUtils.getStatusByCode(account.getStatus());
    			//对参数进行设置
    			jsonObject.put("id", account.getId());
    			jsonObject.put("email",account.getEmail());
    			jsonObject.put("pwd",account.getPwd());
    			jsonObject.put("countrycode",country == null ? account.getCountrycode() : country.getChineseName());
    			jsonObject.put("status",buyerstatus == null ? account.getStatus() : buyerstatus.getCode());
    			jsonObject.put("creditcard", account.getCreditcard());
    			jsonObject.put("postcode", account.getPostcode());
    			jsonObject.put("accountmoney",account.getAccountmoney());
    			jsonObject.put("vpn",account.getVpn());
    			jsonObject.put("buydate",Utils.getDateStr(account.getBuydate(),Utils.format_ymd));
    			jsonObject.put("createdate",Utils.getDateStr(account.getCreatedate(),Utils.format_ymd));
    			jsonArray.add(jsonObject);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("执行查询出错了," + e.getMessage());
    	}
		param.put("total", total);
		param.put("rows",jsonArray);
		return JSONObject.fromObject(param);
	}
	
	/**
	 * 执行下载方法
	 * @param response
	 * @param titleArr
	 * @param accountList
	 */
	private void download(HttpServletResponse response, String[] titleArr, List<Account> accountList) {
	    // 指定下载的文件名
	    response.setHeader("Content-Disposition", "attachment;filename=accountQuery.xls");
	    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    
	    //XSSFWorkbook workbook = exportWorkBook(model);
	    HSSFWorkbook workbook = exportWorkBook(titleArr,accountList);
	    OutputStream os = null;
	    BufferedOutputStream bos = null;
        try {
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            bos.flush();
            workbook.write(bos);
        } catch (IOException e) {
           logger.error("导出excel文件失败了," + e.toString());
        }finally{
        	try {
				bos.close();
				os.close();
			} catch (IOException e) {
				 logger.error("关闭流文件失败了," + e.toString());
			}
        }
	}
	
	/**
	 * 生成写excel文件的对象
	 * @param titleArr
	 * @param accountList
	 * @return
	 */
	private HSSFWorkbook exportWorkBook(String[] titleArr, List<Account> accountList) {
        HSSFWorkbook workbook = new HSSFWorkbook();  
        int celllength = titleArr.length;
		//产生工作表对象  
        HSSFSheet sheet = workbook.createSheet();  
        int size = accountList.size();
        for (int i = 0; i <= size; i++)  
        {  
        	if(i >= SysVariable.excel_max_line){
        		break;
        	}
            HSSFRow row = sheet.createRow((int)i);//创建一行
            //先写对应的标题
            if(i == 0){
            	//创建多列
                for(int j = 0; j < celllength; j++){
                	HSSFCell cell = row.createCell(j);//创建一列  
                	 //cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
                    cell.setCellValue(titleArr[j]);  
                }
            }else{
            	Account account = accountList.get(i - 1);
        		Object[] cellValArr = getCellValue(account);
        		celllength = cellValArr.length;
        		for(int j = 0; j < celllength; j++){
        			HSSFCell cell = row.createCell(j);//创建一列  
                    cell.setCellValue(cellValArr[j] + "");  
        		}
            }
        }  
		return workbook;
	}

	/**
	 * 对实体数据进行转换
	 * @param account
	 * @return
	 */
	private Object[] getCellValue(Account account) {
		Object[] objArr = new Object[]{
				account.getId(),account.getEmail(),account.getPwd(),account.getCountrycode(),account.getType(),
				account.getStatus(),account.getCreditcard(),account.getPostcode(),account.getAccountmoney(),
				account.getVpn(),account.getBuydate(),account.getCreatedate()
		};
		return objArr;
	}

	/**
	 * 封装请求参数对象
	 * @return
	 */
	private Map<String, Object> getRequestParams(String countrycode, String status, String email, int sysid, String type,
			String ip, String buyerId, String startbuydate, String endbuydate, String startcreatedate,
			String endcreatedate) {
		
		Map<String,Object> params = new HashMap<String,Object>();
		if(!Utils.isNullStr(countrycode)){
			params.put("countrycode", countrycode.trim());
		}
		if(!Utils.isNullStr(status)){
			params.put("status", status.trim());
		}
		if(!Utils.isNullStr(email)){
			params.put("email", email.trim());
		}
		if(sysid != 0){
			params.put("sysid", sysid);
		}
		if(!Utils.isNullStr(type)){
			params.put("type", type.trim());
		}
		if(!Utils.isNullStr(ip)){
			params.put("ip", ip.trim());
		}
		if(!Utils.isNullStr(buyerId)){
			params.put("buyerId", buyerId.trim());
		}
		if(!Utils.isNullStr(startbuydate)){
			params.put("startbuydate", startbuydate.trim());
		}
		if(!Utils.isNullStr(endbuydate)){
			params.put("endbuydate", endbuydate.trim());
		}
		if(!Utils.isNullStr(startcreatedate)){
			params.put("startcreatedate", startcreatedate.trim());
		}
		if(!Utils.isNullStr(endcreatedate)){
			params.put("endcreatedate", endcreatedate.trim());
		}
		return params;
	}
	
	public static void main(String[] args) {
		for (Country country : Country.values()) {
			 System.out.println(country.getChineseName() + country.getContentLanguage());  
		 }
	}
}
