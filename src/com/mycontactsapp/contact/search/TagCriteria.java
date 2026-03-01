package com.mycontactsapp.contact.search;

import com.mycontactsapp.contact.model.Contact;

public class TagCriteria implements SearchCriteria{
	private final String tagName;
	
	public TagCriteria(String tagName) {
		if(tagName == null || tagName.isBlank()) throw new IllegalArgumentException("TagName cannot be null");
		this.tagName = tagName;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return contact.getTags().stream()
				.anyMatch(tag -> tag.getName().equals(tagName));
	}

}
