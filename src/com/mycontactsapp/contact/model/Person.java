package com.mycontactsapp.contact.model;

public class Person extends Contact {
	public Person(String name, String phone, String email) {
		super(name, phone, email, "PERSON");
	}
	
	@Override
	public String getType() {
		return "PERSON";
	}
}
