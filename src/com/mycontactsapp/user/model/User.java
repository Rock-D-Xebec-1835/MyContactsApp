package com.mycontactsapp.user.model;

import com.mycontactsapp.user.service.PasswordHasher;
import com.mycontactsapp.user.validation.PasswordValidator;

public abstract class User {
	// Identity is immutable after creation
	private final String email;
	// Security sensitive hashed password
	private String hashedPassword;
	// Profile info
	private String name;
	
	protected User(String email, String hashedPassword, String name){
		if(email == null) throw new IllegalArgumentException("Email cannot be null");
		if(hashedPassword == null) throw new IllegalArgumentException("Password cannot be null");
		if(name == null) throw new IllegalArgumentException("Name cannot be null");

		this.email = email;
		this.hashedPassword = hashedPassword;
		this.name = name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getHashedPassword() {
		return this.hashedPassword;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void updateName(String name) {
		if(name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null");
		this.name = name;
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if(oldPassword == null || newPassword == null) throw new IllegalArgumentException("Password cannot be null");
		String oldHash = PasswordHasher.hash(oldPassword);
		if(!oldHash.equals(this.getHashedPassword())) throw new IllegalArgumentException("Current Password doesn't match");
		PasswordValidator.validatePassword(newPassword);
		this.hashedPassword = PasswordHasher.hash(newPassword);
	}
}
