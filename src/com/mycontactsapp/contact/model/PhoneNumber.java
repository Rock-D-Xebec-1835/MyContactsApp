package com.mycontactsapp.contact.model;

public class PhoneNumber {
	private String number;
	private String type;
	
	public PhoneNumber(String number, String type) {
		this.number = number;
		this.type = type;
	}
	
	public PhoneNumber(PhoneNumber other) {
		this.number = other.number;
		this.type = other.type;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}
}
