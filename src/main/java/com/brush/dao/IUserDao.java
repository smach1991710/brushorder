package com.brush.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.brush.pojo.User;

/**
* 用户操作的dao
* @author 作者 songhao
* @version 创建时间：2017年6月26日 上午10:54:28
*/
@Repository("userDao")
public interface IUserDao {

	/**
	 * 通过用户名密码查找用户
	 * @param map
	 * @return
	 */
	User findUserByUp(Map<String, Object> map);

	/**
	 * 根据用户提交的条件进行查找
	 * @param queryParams
	 * @return
	 */
	List<User> queryUser(Map<String, Object> queryParams);

	/**
	 * 获取总的用户记录数
	 * @param queryParams
	 * @return
	 */
	long getUserCount(Map<String, Object> queryParams);
}


