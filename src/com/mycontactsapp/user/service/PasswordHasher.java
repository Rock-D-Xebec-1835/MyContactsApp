package com.mycontactsapp.user.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {
	private PasswordHasher() {
		
	}
	
	public static String hash(String rawPassword) {
		if(rawPassword == null) throw new IllegalArgumentException("Password cannot be null");
		byte[] hashedBytes;
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			hashedBytes = digest.digest(rawPassword.getBytes());
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("Hashing algorithm not found", e);
		}
		StringBuilder hexString = new StringBuilder();
		for(byte b : hashedBytes) {
			String hex = Integer.toHexString(0xff & b);
			if(hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
