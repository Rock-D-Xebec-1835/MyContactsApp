package com.mycontactsapp.contact.model;

public class Organization extends Contact {
	public Organization(String name, String phone, String email) {
		super(name, phone, email, "ORGANIZATION");
	}
	
	public Organization(Organization other) {
		super(other);
	}
	
	@Override
	public String getType() {
		return "ORGANIZATION";
	}
}
