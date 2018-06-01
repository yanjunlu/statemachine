package com.lu.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class StatemachineApplication implements CommandLineRunner {
	@Autowired
	private StateMachine<States, Events> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(StatemachineApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String , Object> headers = new HashMap<>();
		headers.put("customer", "tom");
		headers.put("order", "orderid1");
		
		stateMachine.start();
		Message<Events> msg = MessageBuilder.createMessage(Events.PAY, new MessageHeaders(headers));
		stateMachine.sendEvent(msg);
		//stateMachine.sendEvent(msg);
		//stateMachine.sendEvent(Events.PAY);
		//stateMachine.sendEvent(Events.RECEIVE);	
		
		//todo 1. 将statemachine存入redis 2. 增加action 执行业务逻辑，更新数据库、guard 判断是否执行状态迁移  3. 通过消息队列触发event 
	}
}
