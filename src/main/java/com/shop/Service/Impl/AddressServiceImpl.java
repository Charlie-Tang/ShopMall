package com.shop.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.Dao.UserAddressDaoMapper;
import com.shop.Service.AddressService;

/**
* @author 作者 tangqichang
* @version 创建时间：2019年2月20日 上午10:00:05
* 类说明
* 我个人认为对数据库进行操作的都要进行事务管理
*/
@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private UserAddressDaoMapper UserAddressDaoMapper;

	@Override
	@Transactional
	public void Delete() {
		
		UserAddressDaoMapper.delete();
	}

	@Override
	@Transactional
	public void Update() {
		
		UserAddressDaoMapper.Update();

	}

	@Override
	@Transactional
	public void Insert() {
		
		UserAddressDaoMapper.Insert();

	}

}
