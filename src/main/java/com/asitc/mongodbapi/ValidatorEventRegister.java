package com.asitc.mongodbapi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

// Workaround for: https://jira.spring.io/browse/DATAREST-524
@Configuration
public class ValidatorEventRegister implements InitializingBean {

	@Autowired
	ValidatingRepositoryEventListener validatingRepositoryEventListener;

	@Autowired
	private Map<String, Validator> validators;

	@Override
	public void afterPropertiesSet() throws Exception {
		final List<String> events = Arrays.asList("beforeCreate");
		for (final Map.Entry<String, Validator> entry : this.validators.entrySet()) {
			events.stream().filter(p -> entry.getKey().startsWith(p)).findFirst()
					.ifPresent(p -> this.validatingRepositoryEventListener.addValidator(p, entry.getValue()));
		}
	}

}