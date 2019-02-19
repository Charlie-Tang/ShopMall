package com.shop.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.error.BusinessException;
import com.shop.error.EmBusinessError;
import com.shop.response.CommonReturnType;


/**
 * @author tangqichang
 *
 * 2019年1月28日-上午11:38:33
 */
public class BaseController {
	//定义ExceptionHandler解决未被controller层吸收的exception
		@ExceptionHandler(Exception.class)
		@ResponseBody
		public Object handlerException(HttpServletRequest request,Exception e) {
			Map<String, Object> responseData = new HashMap<>();
			if (e instanceof BusinessException) {
				BusinessException businessException = (BusinessException)e;
				responseData.put("errCode", businessException.getErrorCode());
				responseData.put("errMsg", businessException.getErrorMsg());
			}
			else {
				responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
				responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
			}
			return CommonReturnType.create(responseData,"fail");
		}
}
