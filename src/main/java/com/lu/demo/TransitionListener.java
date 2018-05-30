package com.lu.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
public class TransitionListener {
	private static Logger logger = LoggerFactory.getLogger(TransitionListener.class);

	@OnTransition(target = "UNPAID")
	public void inited() {
		logger.info("订单创建，待支付");
	}
	
	@OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
	public void paid() {
		logger.info("完成支付，待收货");
	}
	
	@OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
	public void received() {
		logger.info("已收货，完成订单");
	}
}
