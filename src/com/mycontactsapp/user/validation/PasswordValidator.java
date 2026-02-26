package com.mycontactsapp.user.validation;

import java.util.regex.Pattern;

public class PasswordValidator {
	public static void validatePassword(String password) {
		if(password == null || password.isBlank()) throw new IllegalArgumentException("Password cannot be null");
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		if(!Pattern.matches(passwordRegex, password)) throw new IllegalArgumentException("Password must have atleast 1 uppercase, 1 lowercse, 1 number and 1 special character and is atleast 8 characters long");
	}
}
