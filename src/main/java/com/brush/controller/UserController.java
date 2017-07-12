package com.brush.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brush.pojo.LoginUser;
import com.brush.pojo.Role;
import com.brush.pojo.User;
import com.brush.service.IRoleService;
import com.brush.service.IUserService;
import com.brush.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
* 用户的控制类
* @author 作者 songhao
* @version 创建时间：2017年6月19日 下午4:31:00
*/
@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IRoleService roleService;
	
	@RequestMapping(value="/usermanager")
	public String usermanager(HttpServletRequest request,ModelMap model){
		return "user/usermanager";
	} 
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		session.removeAttribute("loginUser");
		return "redirect:/index.jsp";
	}
	
	/**
	 * 根据用户提交的请求字段进行查询
	 * @return
	 */
	@RequestMapping(value="/userList",method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> userList(HttpSession session,
				HttpServletRequest request,
				@RequestParam(value = "roleid",defaultValue="0") int roleid,
				@RequestParam(value = "name",defaultValue="") String name,
				@RequestParam(value = "email",defaultValue="") String email,
				@RequestParam(value = "qq",defaultValue="") String qq,
				@RequestParam(value = "page",defaultValue="1") int page,
				@RequestParam(value = "size",defaultValue="20") int size){
		//获取查询请求参数信息
		Map<String,Object> queryParams = getRequestParams(roleid,name,email,qq);
		//设置from和to参数
    	int from = (page - 1) * size; 
    	queryParams.put("from", from);
    	queryParams.put("size",size);
    	//写结果给前端页面
		Map<String,Object> param = new HashMap<String,Object>();
        JSONArray jsonArray = new JSONArray();
        long total = 0;
    	List<User> userList = new ArrayList<User>();
    	try{
    		//查询得到用户列表
    		userList = userService.queryUser(queryParams);
    		List<Role> roleList = roleService.getAllRoleList();
    		Map<Integer,String> roleMap = new HashMap<Integer,String>();
    		for(Role role : roleList){
    			roleMap.put(role.getId(), role.getRolename());
    		}
    		//查询得到总的记录数
    		total = userService.getUserCount(queryParams);
    		for(User user : userList){
    			JSONObject jsonObject = new JSONObject();
    			jsonObject.put("id", user.getId());
    			jsonObject.put("name",user.getName());
    			jsonObject.put("email",user.getEmail());
    			jsonObject.put("qq",user.getQq());
    			jsonObject.put("roleid",roleMap.get(user.getRoleid()));
    			jsonObject.put("accoutmoney", user.getAccoutmoney());
    			jsonArray.add(jsonObject);
    		}
    	}catch(Exception e){
    		logger.error("执行查询出错了," + e.getMessage());
    	}
		param.put("total", total);
		param.put("rows",jsonArray);
		return JSONObject.fromObject(param);
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(HttpServletRequest request,ModelMap model,
			@RequestParam(value = "user_user",defaultValue="") String user_user,
			@RequestParam(value = "user_pass",defaultValue="") String user_pass){
		if(Utils.isNullStr(user_user) || Utils.isNullStr(user_pass)){
			request.setAttribute("error", "用户名或密码都必填");
			return "loginUi";
		}	
		logger.info("用户:[" + user_user + "]请求登录...");
		String md5pwd = user_pass;
		try {
			md5pwd = Utils.GetMD5Code(user_pass);
		} catch (Exception e) {
			logger.error("获取md5码失败了, " + e.toString());
		}
		//通过用户名和密码登录，返回用户实体信息
		User user = userService.findUserByUp(user_user,md5pwd);
		if(user != null){
			LoginUser loginUser = new LoginUser();
			loginUser.setUser(user);
			request.getSession().setAttribute("loginUser", loginUser);
			return "redirect:/home/index";
		}else{
			request.setAttribute("error", "您输入的帐号或密码不正确，请重新输入");
			return "loginUi";
		}
	}
	
	
	/**
	 * 用户注册入口
	 * @return
	 */
	/*@RequestMapping(value="/createUser",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> createUser(HttpServletRequest request,
			HttpSession session,
    		@RequestParam(value = "user_name") String user_name,
    		@RequestParam(value = "user_pass") String user_pass,
    		@RequestParam(value = "user_email") String user_email,
    		@RequestParam(value = "user_phone") String user_phone,
    		@RequestParam(value = "user_qq") String user_qq){
		
		return null;
		
		Map<String,Object> map = new HashMap<String,Object>();  
		if(Utils.isNullStr(user_name)){
			map.put("msg", false);
			map.put("info", "用户名不可以为空");
			return map;
		}
		//判断一个用户重复的字段
		//1.根据邮箱判断
		//2.根据手机号判断
		//3.根据qq号判断
		//--------------end--------------
		Map<String,Object> termMap = new HashMap<String,Object>();
		termMap.put("user_email", user_email);
		termMap.put("user_phone", user_phone);
		termMap.put("user_qq", user_qq);
		User user = userService.findUserByTerm(termMap);
		if(user != null){
			map.put("msg", false);
			map.put("info", "用户[" + user_name + "]已经存在了，请修改");
			return map;
		}
		//创建一个新用户
		user = new User();
		user.setName(user_name);
		try {
			user.setPassword(Utils.GetMD5Code(user_pass));
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setEmail(user_email);
		user.setPhone(user_phone);
		user.setQq(user_qq);
		user.setMoney(0);
		if(userService.saveUser(user)){
			map.put("msg", true);
		}else{
			map.put("msg", false);
		}
	    return map;
	}*/
	
	/**
	 * 
	 * @param roleid
	 * @param name
	 * @param email
	 * @param qq
	 * @return
	 */
	private Map<String,Object> getRequestParams(int roleid,String name,String email,String qq){
		Map<String,Object> params = new HashMap<String,Object>();
		if(roleid != 0){
			params.put("roleid", roleid);
		}
		if(!Utils.isNullStr(name)){
			params.put("name", name);
		}
		if(!Utils.isNullStr(email)){
			params.put("email", email);
		}
		if(!Utils.isNullStr(qq)){
			params.put("qq", qq);
		}
		return params;
	}
}
