package com.brush.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.brush.dao.IAccountDao;
import com.brush.pojo.Account;
import com.brush.service.IAccountService;

/**
* 账号服务的实现类
* @author 作者 songhao
* @version 创建时间：2017年6月26日 下午5:11:43
*/
@Service("accountService")  
public class AccountServiceImpl implements IAccountService {
	
	@Resource  
    private IAccountDao accountDao;  

	public List<Account> queryAccount(Map<String, Object> queryParams) {
		return accountDao.queryAccount(queryParams);
	}

	public long getAccountCount(Map<String, Object> queryParams) {
		return accountDao.getAccountCount(queryParams);
	}

	public boolean saveAccount(Account account) {
		return accountDao.save(account);
	}
}
