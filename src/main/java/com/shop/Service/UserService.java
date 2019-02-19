package com.shop.Service;

import com.shop.Model.UserModel;
import com.shop.error.BusinessException;

public interface UserService {
	
	Boolean SendOtpToEmail(String email);
	
	//手机号注册
	void registerByTele(UserModel userModel) throws BusinessException;
	
	//邮箱注册
	void registerByEmail(UserModel userModel) throws BusinessException;
	
	//登录(可使用手机号、邮箱号、昵称)
	UserModel validateLogin(String telephone, String encodeByMD5, String Email, String name) throws BusinessException;
	
	//注册一开始就直接判断该邮箱是否已经使用过了
	Boolean selectEmail(String email);
	
	//验证手机号
	Boolean selectTelephone(String telephone);
	
}
