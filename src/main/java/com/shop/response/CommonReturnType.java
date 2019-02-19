/**
 * 
 */
package com.shop.response;

/**
 * @author tangqichang
 *
 * 2019年1月28日-上午9:35:20
 */
public class CommonReturnType {
	//表明对应请求的返回处理结果:success fail
	private String status;
	
	//当status为success时，则返回前端所需要的数据
	private Object data;
	
	//定义一个通用的创建方法
	public static CommonReturnType create(Object result) {
		return CommonReturnType.create(result,"success");
	}

	/**
	 * @param result
	 * @param string
	 * @return
	 */
	public static CommonReturnType create(Object result, String status) {
		CommonReturnType type = new CommonReturnType();
		type.setData(result);
		type.setStatus(status);
		return type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
