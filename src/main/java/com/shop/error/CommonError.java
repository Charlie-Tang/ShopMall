package com.shop.error;

/**
 * @author tangqichang
 *
 * 2019年1月28日-上午9:56:11
 */
public interface CommonError {
	public int getErrorCode();
	
	public String getErrorMsg();
	
	public CommonError setErrorMsg(String ErrorMsg);
}	
