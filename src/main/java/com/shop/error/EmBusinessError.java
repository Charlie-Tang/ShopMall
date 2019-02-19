package com.shop.error;

/**
 * @author tangqichang
 *
 * 2019年1月28日-上午9:59:12
 */
public enum EmBusinessError implements CommonError {
	//通用错误类型10001
	PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
	UNKNOWN_ERROR(10002,"未知错误"),
	
	//20001开头为用户信息相关错误定义
	USER_NOT_EXIST(20001,"用户不存在"),
	USER_LOGIN_FAIL(20002,"用户手机号或密码不存在"),
	USER_NOT_LOGIN(20003,"用户还未登录"),
	USER_RegesiterDuplicateKey(20004,"注册使用账号重复"),
	
	
	STOCK_NOT_EXIST(30001,"库存不足")
	;
	
	private int errCode;
	private String errMsg;
	
	private EmBusinessError(int errCode,String errMsg ) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	
	/* (non-Javadoc)
	 * @see com.example.demo.error.CommonError#getErrorCode()
	 */
	@Override
	public int getErrorCode() {
		return this.errCode;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.error.CommonError#getErrorMsg()
	 */
	@Override
	public String getErrorMsg() {
		return this.errMsg;
	}

	/* (non-Javadoc)
	 * @see com.example.demo.error.CommonError#setErrorMsg(java.lang.String)
	 * 这个接口就可以用来改动通用错误类型中的ErrorMsg
	 */
	@Override
	public CommonError setErrorMsg(String ErrorMsg) {
		this.errMsg = ErrorMsg;
		return this;
	}

}
