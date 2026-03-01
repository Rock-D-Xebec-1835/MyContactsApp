package com.mycontactsapp.user.service;
import com.mycontactsapp.user.auth.Authentication;
import com.mycontactsapp.user.auth.BasicAuth;
import com.mycontactsapp.user.auth.OAuth;
import com.mycontactsapp.user.model.*;
import com.mycontactsapp.user.profile.ProfileManagement;
import com.mycontactsapp.user.session.SessionManager;
import com.mycontactsapp.user.validation.*;
import com.mycontactsapp.contact.model.*;
import com.mycontactsapp.contact.search.SearchCriteria;

import java.util.Map;
import java.util.List;


import java.util.HashMap;
// Service Layer
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
	
	// Login Service
	public static User login(String type, String email, String password) {
		// Check for active sessions
		if(session.isActive()) throw new IllegalStateException("Active session detected. Logout to start a new session");
		Authentication auth;
		if(type.equalsIgnoreCase("BASIC")) {
			// Basic Authentication
			auth = new BasicAuth(users);
		}
		else if(type.equalsIgnoreCase("OAUTH")) {
			// Open Authentication
			auth = new OAuth(users);
		}
		else throw new IllegalArgumentException("Invalid auth type");
		// Authenticate user
		User user = auth.authenticateUser(email, password);
		if(user != null) session.startSession(user);
		return user;
	}
	// Logout Service
	public static void logout() {
		
		session.endSession();
	}
	// Helpers for session management
	public static boolean isLoggedIn() {
		return session.isActive();
	}
	
	public static User getCurrentUser() {
		return session.getCurrentUser();
	}
	// Helpers for Profile Updation
	public static void updateName(String name) {
		ProfileManagement.updateName(getCurrentUser(), name);
	}
	
	public static void changePassword(String oldPassword, String newPassword) {
		ProfileManagement.changePassword(getCurrentUser(), oldPassword, newPassword);
	}
	
	// Contact Management
	
	public static void addContact(Contact contact) {
		if(!session.isActive()) throw new IllegalStateException("Login to add contacts");
		User currentUser = session.getCurrentUser();
		currentUser.addContact(contact);
	}
	
	public static Contact viewContact(String contactId) {
		if(!session.isActive()) throw new IllegalStateException("Login to view Contact");
		User currentUser = session.getCurrentUser();
		return currentUser.getContactById(contactId);
	}
	
	public static void editContact(String id, String newName, String newPhone, String newEmail) {
		if(!session.isActive()) throw new IllegalStateException("Log in to edit contact");
		User currentUser = session.getCurrentUser();
		Contact original = currentUser.getContactById(id);
		if(original == null) throw new IllegalArgumentException("Contact not found");
		// Create a deep copy
		Contact updated;
		if(original instanceof Person) {
			updated = new Person((Person) original);
		}
		else {
			updated = new Organization((Organization) original);
		}
		// Apply changes if not null
		if(newName != null && !newName.isBlank()) {
			updated.updateName(newName);
		}
		if(newPhone != null && !newPhone.isBlank()) {
			updated.updateName(newPhone);
		}
		if(newEmail != null && !newEmail.isBlank()) {
			updated.updateName(newEmail);
		}
		currentUser.replaceContact(updated);
	}
	
	public static void softDeleteContact(String id) {
		if(!session.isActive()) throw new IllegalStateException("Log in to delete contact");
		User currentUser = session.getCurrentUser();
		currentUser.softDeleteContact(id);
	}
	
	public static void hardDeleteContact(String id) {
		if(!session.isActive()) throw new IllegalStateException("Log in to delete contact");
		User currentUser = session.getCurrentUser();
		currentUser.hardDeleteContact(id);
	}
	
	public static void bulkSoftDelete(List<String> ids) {
		if(!session.isActive()) throw new IllegalStateException("Login to bulk delete contacts");
		User user = session.getCurrentUser();
		List<Contact> selected = user.getContactsByIds(ids);
		user.softDeleteContacts(selected);
	}
	
	public static void bulkHardDelete(List<String> ids) {
		if(!session.isActive()) throw new IllegalStateException("Login to bulk delete contacts");
		User user = session.getCurrentUser();
		List<Contact> selected = user.getContactsByIds(ids);
		user.hardDeleteContacts(selected);
	}
	
	public static List<Contact> bulkExport(List<String> ids){
		if(!session.isActive()) throw new IllegalStateException("Login to bulk export contacts");
		User user = session.getCurrentUser();
		return user.getContactsByIds(ids);
	}
	
	// Search and filter based on criteria
	public static List<Contact> search(SearchCriteria criteria){
		if(!session.isActive()) throw new IllegalStateException("Login to search contacts");
		User user = session.getCurrentUser();
		if(criteria == null) throw new IllegalArgumentException("Search criteria cannot be null");
		return user.search(criteria);
	}
}
