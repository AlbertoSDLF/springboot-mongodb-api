package com.asitc.mongodbapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.asitc.mongodbapi.mq.MqInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	@Autowired
	public MappedInterceptor mappedMqInterceptor(final MqInterceptor mqInterceptor) {
		return new MappedInterceptor(new String[] { "/**" }, mqInterceptor);
	}
}
