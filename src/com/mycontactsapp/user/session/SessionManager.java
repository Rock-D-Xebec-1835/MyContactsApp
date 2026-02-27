package com.mycontactsapp.user.session;

import com.mycontactsapp.user.model.User;

public class SessionManager {
	private User currentUser;
	
	public void startSession(User user) {
		this.currentUser = user;
	}
	
	public void endSession() {
		this.currentUser = null;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public boolean isActive() {
		return currentUser != null;
	}
}
