package com.lu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	@Autowired
	private Persist persist;

	@RequestMapping("list")
	public String list() {
		return persist.listDbEntries();
	}

	@RequestMapping("pay")
	public String pay(
			@RequestParam(value = "order", required = true) int order) {
		persist.change(order, Events.PAY);
		return persist.listDbEntries();
	}

	@RequestMapping("receive")
	public String receive(
			@RequestParam(value = "order", required = true) int order) {
		persist.change(order, Events.RECEIVE);
		return persist.listDbEntries();
	}
}
