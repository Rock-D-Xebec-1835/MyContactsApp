package com.mycontactsapp.contact.search;

import com.mycontactsapp.contact.model.Contact;

public class NameCriteria implements SearchCriteria{
	private final String keyword;
	public NameCriteria(String keyword) {
		if(keyword == null || keyword.isBlank()) throw new IllegalArgumentException("Keywoed cannot be null");
		this.keyword = keyword;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return contact.getName().toLowerCase().contains(keyword.toLowerCase());
	}	
}
