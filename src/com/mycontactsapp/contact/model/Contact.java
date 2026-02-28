package com.mycontactsapp.contact.model;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {
	private final UUID id;
	private String name;
	public PhoneNumber phone;
	public Email email;
	private final LocalDateTime createdAt;
	
	public Contact(String name, String phone, String email, String type) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.phone = new PhoneNumber(phone, type);
		this.email = new Email(email, type);
		this.createdAt = LocalDateTime.now();
	}
	
	public String getId() {
		return this.id.toString();
	}
	
	public String getName() {
		return this.name;
	}

	public String getPhone() {
		return this.phone.getNumber();
	}

	public String getEmail() {
		return this.email.getEmail();
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}
	
	public abstract String getType();
	
	@Override
	public String toString() {
	    return "Contact ID: " + id +
	           "\nType: " + getType() +
	           "\nName: " + name +
	           "\nPhone: " + phone.getNumber() +
	           "\nEmail: " + email.getEmail() +
	           "\nCreated At: " + createdAt;
	}
}
