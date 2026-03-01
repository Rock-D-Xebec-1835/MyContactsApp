package com.mycontactsapp.contact.search;
import com.mycontactsapp.contact.model.*;

public class PhoneCriteria implements SearchCriteria{
	private final String phone;
	public PhoneCriteria(String phone) {
		if(phone == null || phone.isBlank()) throw new IllegalArgumentException("Phone cannot be null");
		this.phone = phone;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return contact.getPhone().contains(phone);
	}
}
