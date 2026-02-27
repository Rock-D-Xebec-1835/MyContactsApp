package com.mycontactsapp.user.auth;

import com.mycontactsapp.user.model.User;

public interface Authentication {
	public User authenticateUser(String email, String password);
}
