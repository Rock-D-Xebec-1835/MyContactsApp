package com.mycontactsapp.contact.search;
import java.time.LocalDateTime;

import com.mycontactsapp.contact.model.Contact;

public class DateRangeCriteria implements SearchCriteria {
	private final LocalDateTime after;
	private final LocalDateTime before;
	
	public DateRangeCriteria(LocalDateTime after, LocalDateTime before) {
		this.after = after;
		this.before = before;
	}
	
	@Override
	public boolean matches(Contact contact) {
		LocalDateTime created = contact.getCreatedAt();
		if(after != null && created.isBefore(after)) return false;
		if(before != null && created.isAfter(before)) return false;
		return true;
	}

}
