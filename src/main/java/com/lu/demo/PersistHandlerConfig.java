package com.lu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@Configuration
public class PersistHandlerConfig {
	@Autowired
	private StateMachineFactory<States, Events> stateMachineFactory;

	@Bean
	public Persist persist() {
		return new Persist(persistStateMachineHandler());
	}

	@Bean
	public PersistStateMachineHandler persistStateMachineHandler() {
		return new PersistStateMachineHandler(stateMachineFactory.getStateMachine());
	}
}
