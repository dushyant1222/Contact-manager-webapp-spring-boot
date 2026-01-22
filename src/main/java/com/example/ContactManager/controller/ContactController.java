package com.example.ContactManager.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ContactManager.model.Contact;
import com.example.ContactManager.repo.ContactRepository;
import com.example.ContactManager.service.ContactService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/contacts")
public class ContactController {	
	
	@Autowired
	ContactService contactService;
	
	@Autowired
	ContactRepository contactRepository;
	
	@PostMapping
	public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact){
		Contact savedContact = contactService.createcontact(contact);
		return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contact> getContactbyid(@PathVariable Long id){
		Contact contact = contactService.getContactById(id);
		return ResponseEntity.ok(contact);
	}


	
	@GetMapping
	public ResponseEntity<List<Contact>> getallcontacts(){
		List<Contact> list = contactService.getAllContact();
		return ResponseEntity.ok(list);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Contact> updatecontact(@PathVariable Long id,@Valid @RequestBody Contact contact){
		Contact updated = contactService.updateContact(id, contact);
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletecontact(@PathVariable Long id){
		contactService.deleteContact(id);
		return ResponseEntity.noContent().build();
		
	}
	
	
}
