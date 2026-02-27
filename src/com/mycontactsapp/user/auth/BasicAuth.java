package com.mycontactsapp.user.auth;
import com.mycontactsapp.user.model.User;
import com.mycontactsapp.user.service.PasswordHasher;

import java.util.Map;

public class BasicAuth implements Authentication{
	private Map<String, User> users;
	public BasicAuth(Map<String, User> users) {
		this.users = users;
	}
	
	public User authenticateUser(String email, String password) {
		User user = users.get(email);
		if(user == null) return null;
		String hashedPassword = PasswordHasher.hash(password);
		if(hashedPassword.equals(user.getHashedPassword())) return user;
		return null;
	}
}
