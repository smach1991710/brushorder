package com.brush.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.brush.pojo.Account;

/**
* 账号dao类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 下午5:18:54
*/
@Repository("accountDao")
public interface IAccountDao {

	List<Account> queryAccount(Map<String, Object> queryParams);

	long getAccountCount(Map<String, Object> queryParams);

	boolean save(Account account);
}
