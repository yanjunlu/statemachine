/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lu.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class PersistCommands {

	@Autowired
	private Persist persist;

	@ShellMethod("List orders from db")
	public String list() {
		return persist.listDbEntries();
	}

	@ShellMethod("Pay order")
	public void pay(@ShellOption("Order id") int order) {
		persist.change(order, Events.PAY);
	}

	@ShellMethod("Receive order")
	public void receive(@ShellOption("Order id") int order) {
		persist.change(order, Events.RECEIVE);
	}


}
