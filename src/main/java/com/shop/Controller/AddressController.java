package com.shop.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.DaoObject.UserAddressDao;
import com.shop.Model.UserModel;
import com.shop.Service.AddressService;
import com.shop.response.CommonReturnType;

/**
* @author 作者 tangqichang
* @version 创建时间：2019年2月20日 上午9:51:50
* 类说明
*/

@Controller("Address")
@RequestMapping("/Address")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class AddressController extends BaseController{
	
		
	@Autowired
	private HttpServletRequest HttpServletRequest;
	
	@Autowired
	private AddressService AddressService;
	
		//获取用户地址模块
		@RequestMapping(value="/GetUserAddress",method=RequestMethod.GET)
		@ResponseBody
		public CommonReturnType GetUserAddress()  {
			
			//从之前登录中的信息中取出List
			UserModel userModel = (UserModel)HttpServletRequest.getSession().getAttribute("LOGIN_USER");
			List<UserAddressDao> list = userModel.getAddress();
			
			return CommonReturnType.create(list);
		}
		
		
		//删除收货地址
		@RequestMapping(value="/DeleteUserAddress",method=RequestMethod.GET)
		@ResponseBody
		public CommonReturnType DeleteUserAddress()  {
			
			AddressService.Delete();
			
			return CommonReturnType.create(null);
		}
		
		//修改收货地址
		/**
		 * 在前端修改数据之后 将修改后的数据通过ajax传到后台 
		 * 后台接收数据通过mybatis修改数据库中的数据
		 * @return
		 */
		@RequestMapping(value="/UpdateUserAddress",method=RequestMethod.POST)
		@ResponseBody
		public CommonReturnType UpdateUserAddress()  {
			
			AddressService.Update();
			
			return CommonReturnType.create(null);
		}
		
		//添加收货地址
		/**
		 * 在这里接收到前端传入的值后需要刷新一次界面  
		 * 并将前端传入的值保存到数据库中
		 * @return
		 */
		@RequestMapping(value="/InsertUserAddress",method=RequestMethod.GET)
		@ResponseBody
		public CommonReturnType InsertUserAddress()  {
			
			AddressService.Insert();
			
			return CommonReturnType.create(null);
		}

}
