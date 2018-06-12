package com.lu.demo;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.action.Actions;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;


@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    
	private static Logger logger = LoggerFactory.getLogger(StateMachineConfig.class);
	
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
    	        .action(Actions.errorCallingAction(action(), errorAction()))
    	        .and()
    	    .withExternal()
    	        .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
    	        .event(Events.RECEIVE);
    }

    
    @Bean
    public Guard<States, Events> guard() {
        return new Guard<States, Events>() {

            @Override
            public boolean evaluate(StateContext<States, Events> context) {
            	logger.info("guard orderid:" + context.getMessageHeader("order"));
                return true;
            }
        };
    }
    
    @Bean
    public Action<States, Events> action() {
		return new Action<States, Events>(){

			@Override
			public void execute(StateContext<States, Events> context) {
				String value = (String) context.getExtendedState().getVariables().get("variables");
				logger.info("action orderid:" + context.getMessageHeader("order") + " ,variables=" + value);
				
				if (value == null) {
					context.getExtendedState().getVariables().put("variables", "value1");
				}
				//throw new RuntimeException("MyError");
			}
			
		};
        
    }
    
    @Bean
    public Action<States, Events> errorAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                // RuntimeException("MyError") added to context
                Exception exception = context.getException();
                logger.info("action orderid:" + context.getMessageHeader("order") + " error:"
                		+ exception.getMessage());
            }
        };
    }

}
