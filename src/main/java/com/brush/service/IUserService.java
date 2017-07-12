package com.brush.service;

import java.util.List;
import java.util.Map;

import com.brush.pojo.User;

/**
 * 用户服务类接口
 * @author 作者 songhao
 * @version 创建时间：2017年6月19日 下午4:34:35
 */
public interface IUserService {

	/**
	 * 根据用户名和密码来查询对象
	 * @param user_user
	 * @param md5pwd
	 * @return
	 */
	User findUserByUp(String user_user, String md5pwd);

	/**
	 * 根据手机，eamil或者qq其中之一来进行查询对象
	 * @param termMap
	 * @return
	 */
	User findUserByTerm(Map<String, Object> termMap);

	/**
	 * 保存一个user对象
	 * @param user
	 */
	boolean saveUser(User user);

	/**
	 * 根据请求参数,得到用户列表信息
	 * @param queryParams
	 * @return
	 */
	List<User> queryUser(Map<String, Object> queryParams);

	/**
	 * 获取总的用户记录
	 * @return
	 */
	long getUserCount(Map<String, Object> queryParams);
}
