package com.lu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatemachineApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatemachineApplication.class, args);
	}
	
}
//todo  1. 将 state存入数据库  2. 增加action 执行业务逻辑，更新数据库、guard 判断是否执行状态迁移  
//TODO  3. 通过消息队列触发event 4.1个orderid对应一个statemachine

