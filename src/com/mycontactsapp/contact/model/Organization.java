package com.mycontactsapp.contact.model;

public class Organization extends Contact {
	public Organization(String name, String phone, String email) {
		super(name, phone, email, "ORGANIZATION");
	}
	
	@Override
	public String getType() {
		return "ORGANIZATION";
	}
}
