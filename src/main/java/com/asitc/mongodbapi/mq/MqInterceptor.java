package com.asitc.mongodbapi.mq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class MqInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private MqSender mqSender;

	@Value("${mq.application}")
	private String application;

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		final Boolean success = 200 <= response.getStatus() && response.getStatus() < 300;
		if (success && ex == null) {
			final MqMessage message = new MqMessage();
			this.mqSender.sendMessage(message);
		}
	}
}
