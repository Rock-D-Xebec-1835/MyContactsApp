package com.mycontactsapp.contact.model;

public class Email {
	private String email;
	private String type;
	
	public Email(String email, String type) {
		this.email = email;
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public String getType() {
		return type;
	}
}
