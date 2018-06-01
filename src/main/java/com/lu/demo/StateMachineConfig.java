package com.lu.demo;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
    	states
    	    .withStates()
    	        .initial(States.UNPAID)
    	        .states(EnumSet.allOf(States.class));
    }
    
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
    	transitions
    	    .withExternal()
    	        .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)
    	        .event(Events.PAY)
    	        .guard(guard())
    	        .and()
    	    .withExternal()
    	        .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
    	        .event(Events.RECEIVE);
    }
    
    //通过StateMachineListener监听，注释掉TransitionListener里的@WithStateMachine
    @Override 
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception { 
        config
           .withConfiguration()
           .autoStartup(true)
           .listener(listener()); 
    } 

    
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
    
    @Bean
    public Guard<States, Events> guard() {
        return new Guard<States, Events>() {

            @Override
            public boolean evaluate(StateContext<States, Events> context) {
            	System.out.println("guard " + context.getMessageHeader("customer"));
                return true;
            }
        };
    }

}
