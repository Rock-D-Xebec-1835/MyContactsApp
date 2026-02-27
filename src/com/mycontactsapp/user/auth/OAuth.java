package com.mycontactsapp.user.auth;
import com.mycontactsapp.user.model.User;
import java.util.Map;

public class OAuth implements Authentication {
	private Map<String, User> users;
	public OAuth(Map<String, User> users){
		this.users = users;
	}
	public User authenticateUser(String email, String password) {
		if(users.containsKey(email)) return users.get(email);
		return null;
	}
}
