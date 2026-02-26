package com.mycontactsapp.user.validation;

import java.util.regex.Pattern;

public class EmailValidator {
	public static void validateEmail(String email) {
		if(email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be null");
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if(!Pattern.matches(emailRegex, email)) throw new IllegalArgumentException("Invalid email format");
	}
}
