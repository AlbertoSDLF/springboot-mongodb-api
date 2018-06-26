package com.asitc.mongodbapi.repository.person;

import org.springframework.data.annotation.Id;

public class Person {

	@Id
	private String id;
	private String firstName;
	private String lastName;

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}
}
