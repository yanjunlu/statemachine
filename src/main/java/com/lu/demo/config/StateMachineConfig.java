package com.lu.demo.config;

import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;

import com.lu.demo.constances.Events;
import com.lu.demo.constances.States;

public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

}
