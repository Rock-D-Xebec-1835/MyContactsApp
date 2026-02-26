package com.mycontactsapp.user.service;
import com.mycontactsapp.user.model.*;
import com.mycontactsapp.user.validation.*;
import java.util.Map;

import java.util.HashMap;

public class UserService {
	static Map<String, User> users = new HashMap<>();
	// Register User
	public static User registerUser(String type, String email, String rawPassword, String name) {
		EmailValidator.validateEmail(email);
		PasswordValidator.validatePassword(rawPassword);
		
		if(users.containsKey(email)) throw new RuntimeException("User already exists with this email");
		
		// Hash Password before creating the user;
		String hashedPassword = PasswordHasher.hash(rawPassword);
		// Factory method used to create desired user object
		User user;
		switch(type.toUpperCase()) {
		case "FREE": user = new FreeUser(email, hashedPassword, name); break;
		case "PREMIUM": user = new PremiumUser(email, hashedPassword, name); break;
		default: throw new IllegalArgumentException("Invalid user type");
		}
		users.put(email, user);
		return user;
	}
}
