package com.mycontactsapp.contact.search;
import com.mycontactsapp.contact.model.*;

public class FrequentlyContactedCriteria implements SearchCriteria {
	private final int threshold;
	public FrequentlyContactedCriteria(int threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return contact.getContactCount() >= threshold;
	}
}
