package com.mycontactsapp.user.model;

import com.mycontactsapp.user.service.PasswordHasher;
import com.mycontactsapp.user.validation.PasswordValidator;
import com.mycontactsapp.contact.model.*;
import java.util.List;
import java.util.ArrayList;

public abstract class User {
	// Identity is immutable after creation
	private final String email;
	// Security sensitive hashed password
	private String hashedPassword;
	// Profile info
	private String name;
	
	private final List<Contact> contacts = new ArrayList<>();
	
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
	
	public void addContact(Contact contact) {
		if(contact == null) throw new IllegalArgumentException("Contact cannot be null");
		for (Contact existing : contacts) {
		    if (existing.getPhone().equals(contact.getPhone())) {
		        throw new IllegalArgumentException("Contact with this number already exists");
		    }
		}
		this.contacts.add(contact);
	}
	
	public List<Contact> getContacts(){
		List<Contact> activeContacts = new ArrayList<>();
		for(Contact contact : contacts) {
			if(!contact.isDeleted()) activeContacts.add(contact);
		}
		return activeContacts;
	}
	
	public Contact getContactById(String id) {
		for(Contact contact : contacts) {
			if(contact.getId().equals(id)) return contact;
		}
		return null;
	}
	
	public void replaceContact(Contact updatedContact) {
		for(int i = 0; i < contacts.size(); i++) {
			if(contacts.get(i).getId().equals(updatedContact.getId())) {
				contacts.set(i, updatedContact);
				return;
			}
		}
		throw new IllegalArgumentException("Contact not found");
	}
	
	public void softDeleteContact(String id) {
		Contact contact = getContactById(id);
		if(contact == null) throw new IllegalArgumentException("Contact not found");
		contact.markDeleted();
	}
	
	public void hardDeleteContact(String id) {
		Contact toRemove = null;
		for(Contact contact : contacts) {
			if(contact.getId().equals(id)) {
				toRemove = contact;
				break;
			}
		}
		
		if(toRemove == null) throw new IllegalArgumentException("Contact not found");
		contacts.remove(toRemove);
	}
	
	public List<Contact> getContactsByIds(List<String> ids){
		return contacts.stream()
				.filter(c -> ids.contains(c.getId()))
				.filter(c -> !c.isDeleted())
				.toList();
	}
	
	public void softDeleteContacts(List<Contact> selected) {
		selected.forEach(Contact::markDeleted);
	}
	
	public void hardDeleteContacts(List<Contact> selected) {
		contacts.removeAll(selected);
	}
	
	public List<Contact> filterContactsByName(String text){
		return contacts.stream()
				.filter(c -> !c.isDeleted())
				.filter(c -> c.getName().toLowerCase().contains(text.toLowerCase()))
				.toList();
	}
	
}
