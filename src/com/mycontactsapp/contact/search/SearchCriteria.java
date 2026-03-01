package com.mycontactsapp.contact.search;

import com.mycontactsapp.contact.model.Contact;

public interface SearchCriteria {
	boolean matches(Contact contact);
}
