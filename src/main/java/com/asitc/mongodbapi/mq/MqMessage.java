package com.asitc.mongodbapi.mq;

import java.io.Serializable;

public class MqMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String application;
	private String resource;
	private String body;
	private String method;
	private String parameters;

	public MqMessage() {
	}

	public MqMessage(final String application, final String resource, final String body, final String method,
			final String parameters) {
		super();
		this.application = application;
		this.resource = resource;
		this.body = body;
		this.method = method;
		this.parameters = parameters;
	}

	public String getApplication() {
		return this.application;
	}

	public String getBody() {
		return this.body;
	}

	public String getMethod() {
		return this.method;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setApplication(final String application) {
		this.application = application;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public void setParameters(final String parameters) {
		this.parameters = parameters;
	}

	public String getResource() {
		return this.resource;
	}

	public void setResource(final String resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "Message [application=" + this.application + ", body=" + this.body + ", method=" + this.method
				+ ", parameters=" + this.parameters + "]";
	}

}
