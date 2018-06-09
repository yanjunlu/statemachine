package com.lu.demo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnEventNotAccepted;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.OnTransitionEnd;
import org.springframework.statemachine.annotation.OnTransitionStart;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
public class TransitionListener {
	private static Logger logger = LoggerFactory.getLogger(TransitionListener.class);

	@OnTransition(target = "UNPAID")
	public void inited() {
		logger.info("订单创建，待支付");
	}
	
	@OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
	public void paid(StateContext<States, Events> stateContext,
			@EventHeaders Map<String, Object> headers,
            ExtendedState extendedState,
            StateMachine<States, Events> stateMachine,
            Message<Events> message,
            Exception e) {
		logger.info("完成支付，待收货");
	}
	
	@OnTransitionStart(source = "WAITING_FOR_RECEIVE", target = "DONE")
	public void startreceive() {
		
		logger.info("已收货，完成订单 start");
	}
	
	@OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
	public void receive() {
		
		logger.info("已收货，完成订单");
	}
	
	@OnTransitionEnd(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void received() {
		
		logger.info("已收货，完成订单 end");
	}
	
	@OnEventNotAccepted
    public void anyEventNotAccepted(StateContext<States, Events> stateContext) {
		
		logger.info("event not accepted: " + stateContext.getEvent());
    }
}
