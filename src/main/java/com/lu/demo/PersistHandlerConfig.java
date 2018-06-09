package com.lu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;

@Configuration
public class PersistHandlerConfig {
	@Autowired
	private StateMachine<States, Events> stateMachine;

	@Bean
	public Persist persist() {
		return new Persist(persistStateMachineHandler());
	}

	@Bean
	public PersistStateMachineHandler persistStateMachineHandler() {
		return new PersistStateMachineHandler(stateMachine);
	}
}
