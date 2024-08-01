package com.qyx.showtick.simplepay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.qyx.showtick.common.mapper")
public class SimplePayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplePayApplication.class, args);
	}

}
