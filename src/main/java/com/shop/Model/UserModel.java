package com.shop.Model;

import java.util.List;

import com.shop.DaoObject.UserAddressDao;
import com.shop.DaoObject.UserAllreadyBuyDao;

public class UserModel {
	
	private String name;
	
	private Integer age;
	
	private Byte gender;
	
	private Long telephone;
	
	private String email;
	
	//注册方式
	private String register_mode;
	
	//收货地址 该数据另起一表
	private List<UserAddressDao> address;
	
	//加密的密码  另起一表
	private String encrpt_password;
	
	//购买了的商品  其实还可以分为下单啊  处于订单状态啊  还在配送啊这样的信息
	//这里我就直接全都放在一张表中了
	private List<UserAllreadyBuyDao> AllreadyBuyItems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public Long getTelephone() {
		return telephone;
	}

	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegister_mode() {
		return register_mode;
	}

	public void setRegister_mode(String register_mode) {
		this.register_mode = register_mode;
	}

	
	public List<UserAddressDao> getAddress() {
		return address;
	}

	public void setAddress(List<UserAddressDao> address) {
		this.address = address;
	}

	public String getEncrpt_password() {
		return encrpt_password;
	}

	public void setEncrpt_password(String encrpt_password) {
		this.encrpt_password = encrpt_password;
	}

	public List<UserAllreadyBuyDao> getAllreadyBuyItems() {
		return AllreadyBuyItems;
	}

	public void setAllreadyBuyItems(List<UserAllreadyBuyDao> allreadyBuyItems) {
		AllreadyBuyItems = allreadyBuyItems;
	}

	
	
}
