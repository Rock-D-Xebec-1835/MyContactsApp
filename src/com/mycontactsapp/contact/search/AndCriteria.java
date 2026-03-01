package com.mycontactsapp.contact.search;
import com.mycontactsapp.contact.model.*;
import java.util.List;

public class AndCriteria implements SearchCriteria{
	
	private final List<SearchCriteria> criteriaList;
	
	public AndCriteria(List<SearchCriteria> criteriaList) {
		if(criteriaList == null || criteriaList.isEmpty()) throw new IllegalArgumentException("Criteria list cannot be null");
		this.criteriaList = criteriaList;
	}
	
	@Override
	public boolean matches(Contact contact) {
		return criteriaList.stream().allMatch(criteria -> criteria.matches(contact));
	}
	
}
