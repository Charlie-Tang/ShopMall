package com.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages= {"com.shop"})
@MapperScan("com.shop.Dao")
public class ShopMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopMallApplication.class, args);
	}

}

