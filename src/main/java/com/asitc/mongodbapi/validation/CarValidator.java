package com.asitc.mongodbapi.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.asitc.mongodbapi.repository.car.Car;

@Component("beforeCreateCarValidator")
public class CarValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return Car.class.equals(clazz);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		final Car car = (Car) obj;

		if (!StringUtils.hasText(car.getMake())) {
			errors.rejectValue("make", "make.empty");
		}

		if (!StringUtils.hasText(car.getModel())) {
			errors.rejectValue("model", "model.empty");
		}
	}

}