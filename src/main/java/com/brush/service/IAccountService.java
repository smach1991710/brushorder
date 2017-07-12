package com.brush.service;

import java.util.List;
import java.util.Map;

import com.brush.pojo.Account;

/**
 * 账号服务类接口
 * @author 作者 songhao
 * @version 创建时间：2017年6月19日 下午4:34:35
 */
public interface IAccountService {

	/**
	 * 根据请求参数,得到用户列表信息
	 * @param queryParams
	 * @return
	 */
	List<Account> queryAccount(Map<String, Object> queryParams);

	/**
	 * 获取总的用户记录
	 * @return
	 */
	long getAccountCount(Map<String, Object> queryParams);

	/**
	 * 保存账户信息
	 * @param account
	 */
	boolean saveAccount(Account account);
}
