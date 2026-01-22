package com.example.ContactManager.exception;

public class DuplicateEmailException extends RuntimeException {
	
	public DuplicateEmailException(String message) {
		super(message);
	}

}
