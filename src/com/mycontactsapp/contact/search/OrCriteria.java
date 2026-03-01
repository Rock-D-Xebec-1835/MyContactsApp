package com.mycontactsapp.contact.search;
import com.mycontactsapp.contact.model.*;
import java.util.List;


public class OrCriteria implements SearchCriteria {

	private final List<SearchCriteria> criteriaList;
	public OrCriteria(List<SearchCriteria> criteriaList) {
		if(criteriaList == null || criteriaList.isEmpty()) throw new IllegalArgumentException("Criteria list cannot be null");
		this.criteriaList = criteriaList;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return criteriaList.stream().anyMatch(criteria -> criteria.matches(contact));
	}

}
