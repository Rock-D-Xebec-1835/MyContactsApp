package com.mycontactsapp.contact.model;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Contact {
	private final UUID id;
	private String name;
	public PhoneNumber phone;
	public Email email;
	private final LocalDateTime createdAt;
	private boolean deleted = false;
	private int contactCount = 0;
	
	public Contact(String name, String phone, String email, String type) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.phone = new PhoneNumber(phone, type);
		this.email = new Email(email, type);
		this.createdAt = LocalDateTime.now();
	}
	
	protected Contact(Contact other) {
		this.id = other.id;
		this.name = other.name;
		this.phone = other.phone;
		this.email = other.email;
		this.createdAt = other.createdAt;
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
	
	public void updateName(String newName) {
		if(newName == null || newName.isBlank()) throw new IllegalArgumentException("Contact cannot be null");
		this.name = newName;
	}
	
	public void updatePhone(String newPhone) {
		 this.phone = new PhoneNumber(newPhone, getType());
	}
	
	public void updateEmail(String newEmail) {
		this.email = new Email(newEmail, getType());
	}
	
	public void markDeleted() {
		this.deleted = true;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void incrementContactCount() {
		contactCount++;
	}
	
	public int getContactCount() {
		return this.contactCount;
	}
}
