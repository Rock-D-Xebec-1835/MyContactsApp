package com.mycontactsapp.contact.tag;
import java.util.Objects;

public class Tag {
	private final String name;
	
	public Tag(String name) {
		if(name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null");
		this.name = name.trim().toLowerCase();
	}
	
	public String getName() {
		return this.name;
	}
	
	// For set uniqueness
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Tag)) return false;
		
		Tag other = (Tag) obj;
		return name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
