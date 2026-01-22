package com.example.ContactManager.exception;

public class ContactNotFoundException extends RuntimeException {
	
	public ContactNotFoundException(String message) {
		super(message);
	}
	
}
