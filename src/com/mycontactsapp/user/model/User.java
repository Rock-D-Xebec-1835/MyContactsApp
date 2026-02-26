package com.mycontactsapp.user.model;

public abstract class User {
	// Identity is immutable after creation
	private final String email;
	// Security sensitive hashed password
	private final String hashedPassword;
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
	
}
