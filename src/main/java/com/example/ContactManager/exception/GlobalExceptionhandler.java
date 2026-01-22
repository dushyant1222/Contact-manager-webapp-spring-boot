package com.example.ContactManager.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ContactManager.model.Contact;

@RestControllerAdvice
public class GlobalExceptionhandler {
	
	@ExceptionHandler(ContactNotFoundException.class)
	public ResponseEntity<Object> handlecontactnotfound(ContactNotFoundException ex){
		return buildresponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}
		
	
	
	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Object> handleduplicateemail(DuplicateEmailException ex){
		return buildresponse(HttpStatus.CONFLICT, ex.getMessage());
		
	}
	
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<Object> handleinvalidinput(InvalidInputException ex){
		return buildresponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handlegeneralexception(Exception ex){
		ex.printStackTrace();
		return buildresponse(HttpStatus.INTERNAL_SERVER_ERROR, "something went wrong");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handlevalidationerrors(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		
	}
	
	private ResponseEntity<Object> buildresponse(HttpStatus status, String message){
		Map<String, Object> body = new HashMap<>();
		body.put("time stamp", LocalDateTime.now());
		body.put("Status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		
		
		return new ResponseEntity<>(body, status);
		
	}
	
	
}
	

