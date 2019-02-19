package com.shop.Service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.shop.Dao.UserAddressDaoMapper;
import com.shop.Dao.UserAllreadyBuyDaoMapper;
import com.shop.Dao.UserDaoMapper;
import com.shop.Dao.UserPassWordDaoMapper;
import com.shop.DaoObject.UserDao;
import com.shop.DaoObject.UserPassWordDao;
import com.shop.Model.UserModel;
import com.shop.Service.UserService;
import com.shop.error.BusinessException;
import com.shop.error.EmBusinessError;
import com.shop.validator.ValidationResult;
import com.shop.validator.ValidatorImpl;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private ValidatorImpl validator;
	
	@Autowired
	private UserDaoMapper UserDaoMapper;
	
	@Autowired
	private UserPassWordDaoMapper UserPassWordDaoMapper;
	
	@Autowired
	private UserAddressDaoMapper UserAddressDaoMapper;
	
	@Autowired 
	private UserAllreadyBuyDaoMapper UserAllreadyBuyDaoMapper;

	@Override
	public Boolean SendOtpToEmail(String email) {
//		
//		Properties properties = new Properties();
//        properties.setProperty("mail.smtp.host","smtp.qq.com");//发送邮箱服务器
//        properties.setProperty("mail.smtp.port","465");//发送端口
//        properties.setProperty("mail.smtp.auth","true");//是否开启权限控制
//        properties.setProperty("mail.debug","true");//true 打印信息到控制台
//        properties.setProperty("mail.transport","smtp");//发送的协议是简单的邮件传输协议
//        properties.setProperty("mail.smtp.ssl.enable","true");
//        
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("604623486@qq.com","xxxxxdfda");
//            }
//        });
//        
//        Message message = new MimeMessage(session);
//        try {
//        	//这个是发件人
//            message.setFrom(new InternetAddress("2869441798@qq.com"));
//
//        //设置收件人
//        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));//收件人
//        //设置主题
//        message.setSubject("验证码");
//        //设置邮件正文  第二个参数是邮件发送的类型
//        message.setContent("2569658","text/html;charset=UTF-8");
//        //发送一封邮件
//            Transport transport = session.getTransport();
//            transport.connect("2869441798@qq.com","xxxxxda");
//            Transport.send(message);
//            System.out.println("执行了");
//		    } catch (MessagingException e) {
//		        e.printStackTrace();
//		    }finally {
//
//        }
//
//		
//		
		return true;
	}
	
	@Override
	public void registerByTele(UserModel userModel) throws BusinessException {
		if (userModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		
//		if (StringUtils.isEmpty(userModel.getName()) 
//				|| userModel.getGender() == null 
//				|| userModel.getAge() == null
//				|| StringUtils.isEmpty(userModel.getTelephone())) {
//			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//		}
		ValidationResult result = validator.validate(userModel);
		if (result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
		}
		
		UserDao userDao = convertFromModel(userModel);
		try {
			UserDaoMapper.insertSelective(userDao);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(EmBusinessError.USER_RegesiterDuplicateKey,"手机号已重复注册");
		}
		
		//尽量避免在数据库中使用null字段
		UserPassWordDao userPassWordDao = convertPasswordFromModel(userModel,userDao);
		UserPassWordDaoMapper.insertSelective(userPassWordDao);
	}
	
	@Override
	public void registerByEmail(UserModel userModel) throws BusinessException {
		if (userModel==null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		
		ValidationResult result = validator.validate(userModel);
		if (result.isHasErrors()) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
		}
		
		UserDao userDao = convertFromModel(userModel);
		try {
			UserDaoMapper.insertSelective(userDao);
		} catch (DuplicateKeyException e) {
			throw new BusinessException(EmBusinessError.USER_RegesiterDuplicateKey,"邮箱号已重复注册");
		}
		
		//尽量避免在数据库中使用null字段
		UserPassWordDao userPassWordDao = convertPasswordFromModel(userModel,userDao);
		UserPassWordDaoMapper.insertSelective(userPassWordDao);
	}
	
	
	private UserPassWordDao convertPasswordFromModel(UserModel userModel, UserDao userDao) {
		
		if (userModel==null) {
			return null;
		}
		
		UserPassWordDao userPassWordDao = new UserPassWordDao();
		userPassWordDao.setEncrptPassword(userModel.getEncrpt_password());
		System.out.println(userDao.getId());
		userPassWordDao.setUserId(userDao.getId());
		return userPassWordDao;
		
	}

	private UserDao convertFromModel(UserModel userModel) {
		
		if (userModel==null) {
			return null;
		}
		
		UserDao userDao = new UserDao();
		BeanUtils.copyProperties(userModel, userDao);
		userDao.setTelephone(String.valueOf(userModel.getTelephone()));
		userDao.setRegisterMode(userModel.getRegister_mode());
		
		return userDao;
	}

	
	
	@Override
	public UserModel validateLogin(String telephone, String password, String Email, String name) throws BusinessException {
		
		UserDao userDao = null;
		//首先做传参判断 
		if (telephone!=null) {
			userDao = UserDaoMapper.selectByTele(telephone);
		}
		else if (Email!=null) {
			userDao = UserDaoMapper.selectByEmail(Email);
		}
		else if (name!=null) {
			userDao = UserDaoMapper.selectByName(name);
		}
		
		if (userDao==null) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		
		UserPassWordDao userPassWordDao = UserPassWordDaoMapper.selectByUserId(userDao.getId());
		UserModel userModel = convertFromDao(userDao,userPassWordDao);
		
		userModel.setAddress(UserAddressDaoMapper.selectByUserId(userDao.getId()));
		userModel.setAllreadyBuyItems(UserAllreadyBuyDaoMapper.selectByUserId(userDao.getId()));
		
		//比对用户信息内加密
		if (!StringUtils.equals(password, userModel.getEncrpt_password())) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		
		return userModel;
	}
	//UserModel接收UserDao和UserPassWordDao信息
	private UserModel convertFromDao(UserDao userDao, UserPassWordDao userPassWordDao) {
		if (userDao==null||userPassWordDao==null) {
			return null;
		}
		
		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userDao, userModel);
		if (userDao.getTelephone()!=null && !userDao.getTelephone().equals("")) {
			userModel.setTelephone(Long.valueOf(userDao.getTelephone()));
		}
		userModel.setRegister_mode(userDao.getRegisterMode());
		userModel.setEncrpt_password(userPassWordDao.getEncrptPassword());
		
		return userModel;
	}

	@Override
	public Boolean selectEmail(String email) {
		
		UserDao userDao = UserDaoMapper.selectByEmail(email);
		if (userDao!=null) {
			//如果可根据邮箱找到用户则返回true
			return true;
		}
		return false;
	}

	@Override
	public Boolean selectTelephone(String telephone) {
		
		UserDao userDao = UserDaoMapper.selectByTele(telephone);
		if (userDao!=null) {
			//如果可根据邮箱找到用户则返回true
			return true;
		}
		return false;
	}

}
