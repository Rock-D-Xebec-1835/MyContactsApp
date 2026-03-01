package com.mycontactsapp.contact.search;
import com.mycontactsapp.contact.model.*;
public class EmailCriteria implements SearchCriteria {
	private final String email;
	
	public EmailCriteria(String email) {
		if(email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be null");
		this.email = email;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return contact.getEmail().contains(email.toLowerCase());
	}
}
