package com.mycontactsapp.user.service;
import com.mycontactsapp.user.auth.Authentication;
import com.mycontactsapp.user.auth.BasicAuth;
import com.mycontactsapp.user.auth.OAuth;
import com.mycontactsapp.user.model.*;
import com.mycontactsapp.user.session.SessionManager;
import com.mycontactsapp.user.validation.*;
import java.util.Map;

import java.util.HashMap;

public class UserService {
	private static Map<String, User> users = new HashMap<>();
    private static SessionManager session = new SessionManager();
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
	
	public static User login(String type, String email, String password) {
		if(session.isActive()) throw new IllegalStateException("Active session detected. Logout to start a new session");
		Authentication auth;
		if(type.equalsIgnoreCase("BASIC")) {
			auth = new BasicAuth(users);
		}
		else if(type.equalsIgnoreCase("OAUTH")) {
			auth = new OAuth(users);
		}
		else throw new IllegalArgumentException("Invalid auth type");
		User user = auth.authenticateUser(email, password);
		if(user != null) session.startSession(user);
		return user;
	}
	
	public static void logout() {
		session.endSession();
	}
	
	public static boolean isLoggedIn() {
		return session.isActive();
	}
	
	public static User getCurrentUser() {
		return session.getCurrentUser();
	}
	
	
	
	
}
