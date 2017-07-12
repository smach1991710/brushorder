package com.brush.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.brush.dao.IUserDao;
import com.brush.pojo.User;
import com.brush.service.IUserService;

/**
* 用户服务的实现类
* @author 作者 songhao
* @version 创建时间：2017年6月20日 下午4:21:58
*/
@Service("userService")  
public class UserServiceImpl implements IUserService{
	
	@Resource  
    private IUserDao userDao;  

	public User findUserByUp(String user_user, String md5pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email", user_user);
		map.put("pwd", md5pwd);
		return userDao.findUserByUp(map);
	}

	public User findUserByTerm(Map<String, Object> termMap) {
		return null;
	}

	public boolean saveUser(User user) {
		return false;
	}

	public List<User> queryUser(Map<String, Object> queryParams) {
		return userDao.queryUser(queryParams);
	}

	public long getUserCount(Map<String, Object> queryParams) {
		return userDao.getUserCount(queryParams);
	}
}
