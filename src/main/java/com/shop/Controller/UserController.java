package com.shop.Controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.shop.Model.UserModel;
import com.shop.Service.UserService;
import com.shop.error.BusinessException;
import com.shop.error.EmBusinessError;
import com.shop.response.CommonReturnType;

import sun.misc.BASE64Encoder;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class UserController extends BaseController{
	
	@Autowired
	private HttpServletRequest HttpServletRequest;
	
	@Autowired
	private UserService UserService;
	
		//用户向邮箱发送验证码接口
		@RequestMapping(value="/getEmailotp",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
		@ResponseBody
		public CommonReturnType SendEmailOtp(@RequestParam(value="email")String email) throws BusinessException {
			
			//在注册之前我认为需要直接去数据库中检索该邮箱是否被使用过
			Boolean flag = UserService.selectEmail(email);
			if (flag==true) {
				throw new BusinessException(EmBusinessError.USER_RegesiterDuplicateKey,"邮箱号已重复注册，请更换邮箱号注册");
			}
			
			//生成一个随机6位验证码
			String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
			StringBuilder sb=new StringBuilder(6);
			for(int i=0;i<6;i++)
			{
			char ch=str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
			}
			//将我们的信息和该验证码传入session域中
			HttpServletRequest.getSession().setAttribute("emailotpCode", sb.toString());
			
			//向邮箱发送验证码 暂时不使用   现在的邮箱服务器实在是有点鸡肋
			System.out.println(email +" " +sb.toString());
			
			return CommonReturnType.create(null);
		}
		
		//用户向手机发送otp验证码
		@RequestMapping(value="/getotp",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
		@ResponseBody
		public CommonReturnType SendOtp(@RequestParam(value="telephone")String telephone) throws BusinessException {
			
			Boolean flag = UserService.selectTelephone(telephone);
			if (flag==true) {
				throw new BusinessException(EmBusinessError.USER_RegesiterDuplicateKey,"手机号已重复注册，请更换手机号注册");
			}
			
			//生成一个随机6位验证码 手机验证码通常是6位数字
			String str="0123456789";
			StringBuilder sb=new StringBuilder(6);
			for(int i=0;i<6;i++)
			{
			char ch=str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
			}
			//将我们的信息和该验证码传入session域中
			HttpServletRequest.getSession().setAttribute("telephoneotpCode", sb.toString());
			
			//向邮箱发送验证码 暂时不使用   现在的邮箱服务器实在是有点鸡肋
			System.out.println(telephone + " " +sb.toString());
			
			return CommonReturnType.create(null);
		}
		
		//手机用户注册接口
		@RequestMapping(value="/RegisterByTele",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
		@ResponseBody
		public CommonReturnType registerByTele(@RequestParam(value="telephone")Long telephone,
				@RequestParam(value="otpCode")String otpCode,
				@RequestParam(value="name")String name,
				@RequestParam(value="gender")Byte gender,
				@RequestParam(value="age")Integer age,
				@RequestParam(value="password")String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
			
			//验证手机号和对应的otpcode是否相符合  在Session中存放的是Object对象
			String inSessionotpcode = (String) this.HttpServletRequest.getSession().getAttribute("telephoneotpCode");
			if (!StringUtils.equals(otpCode, inSessionotpcode)) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
			}
			
			//用户注册流程
			UserModel userModel = new UserModel();
			userModel.setName(name);
			userModel.setGender(gender);
			userModel.setAge(age);
			userModel.setTelephone(telephone);
			userModel.setRegister_mode("ByTele");
			userModel.setEncrpt_password(this.EncodeByMD5(password));
			
			UserService.registerByTele(userModel);
			
			return CommonReturnType.create(null);
		}
		
		//邮箱用户注册接口
		@RequestMapping(value="/RegisterByEmail",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
		@ResponseBody
		public CommonReturnType registerByEmail(@RequestParam(value="email")String email,
				@RequestParam(value="emailotpCode")String otpCode,
				@RequestParam(value="name")String name,
				@RequestParam(value="gender")Byte gender,
				@RequestParam(value="age")Integer age,
				@RequestParam(value="password")String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
			
			//验证手机号和对应的otpcode是否相符合  在Session中存放的是Object对象
			String inSessionotpcode = (String) this.HttpServletRequest.getSession().getAttribute("emailotpCode");
			if (!StringUtils.equals(otpCode, inSessionotpcode)) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"邮箱验证码不符合");
			}
			
			//用户注册流程
			UserModel userModel = new UserModel();
			userModel.setName(name);
			userModel.setGender(gender);
			userModel.setAge(age);
			userModel.setEmail(email);
			userModel.setRegister_mode("ByEmail");
			userModel.setEncrpt_password(this.EncodeByMD5(password));
			
			UserService.registerByEmail(userModel);
			
			return CommonReturnType.create(null);
		}
		
		//将密码转码为MD5格式
		public String EncodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException
		{
			//确定计算方法
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64Encoder = new BASE64Encoder();
			//加密字符串
			String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
			return newstr;
		}
		
		//用户登录接口(可使用手机号、邮箱号或是昵称登录)
		@RequestMapping(value="/login",method=RequestMethod.POST,consumes="application/x-www-form-urlencoded")
		@ResponseBody
		public CommonReturnType login(@RequestParam(value="telephone",required=false)String telephone,
				@RequestParam(value="Email",required=false)String Email,
				@RequestParam(value="name",required=false)String name,
				@RequestParam(value="password")String password) throws BusinessException, NoSuchAlgorithmException, UnsupportedEncodingException {
			
			//入参校验(在这里我认为密码不为空就好  逻辑其实可以在前端实现)
			if (org.apache.commons.lang3.StringUtils.isEmpty(password)) {
				throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
			}
			
			UserModel userModel = null;
			if (telephone!=null) {
				userModel = UserService.validateLogin(telephone, this.EncodeByMD5(password),null,null);
			}
			if (Email!=null) {
				userModel = UserService.validateLogin(null, this.EncodeByMD5(password),Email,null);
			}
			if (name!=null) {
				userModel = UserService.validateLogin(null, this.EncodeByMD5(password),null,name);
			}
			
			//将登陆凭证加入到用户登陆成功的session内
			HttpServletRequest.getSession().setAttribute("IS_LOGIN", true);
			HttpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
			
			return CommonReturnType.create(null);
		}
		
		//获取用户地址模块
		@RequestMapping(value="/GetUserAddress",method=RequestMethod.GET)
		@ResponseBody
		public CommonReturnType GetUserAddress()  {
			
			//从之前登录中的信息中取出List
			UserModel userModel = (UserModel)HttpServletRequest.getSession().getAttribute("LOGIN_USER");
			return CommonReturnType.create(userModel.getAddress());
		}
		
		//获取用户订单模块
		
		
}
