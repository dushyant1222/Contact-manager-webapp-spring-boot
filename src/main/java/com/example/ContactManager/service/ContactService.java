package com.example.ContactManager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ContactManager.exception.ContactNotFoundException;
import com.example.ContactManager.exception.DuplicateEmailException;
import com.example.ContactManager.exception.InvalidInputException;
import com.example.ContactManager.model.Contact;
import com.example.ContactManager.repo.ContactRepository;

@Service
public class ContactService {
	
	@Autowired
	ContactRepository contactRepository;
	
	public Contact createcontact(Contact contact) {
		
		 //can the contact be null? NO
		 if(contact == null) throw new ContactNotFoundException("Contact Should not be null"); //what if the contact is null?
		 
		 //is email present? Must be || validation of the email
		 if(contact.getEmail() == null || contact.getEmail().trim().isEmpty()) {
			 throw new ContactNotFoundException("Here the email is compulsory");
		 }
		 
		 //here the email should be normalize to the lower case)
		 //becuase the email cannot be in the upper case
		 String normalized_email = contact.getEmail().trim().toLowerCase();
		 contact.setEmail(normalized_email);
		 
		 //Now we will check the uniqueness that is this email already exist or not if yes than
		 //it will ive the runtime error and if not then it will gets saved
		 Optional<Contact> existing = contactRepository.findByEmail(normalized_email);
		 if(existing.isPresent()) {
			 throw new DuplicateEmailException("Email id is already present..");
		 }
		 
		 //We have to set the system managed fields
		 contact.setDeleted(false);
		 contact.setCreated_at(LocalDateTime.now());
		 contact.setUpdated_at(LocalDateTime.now());
		 
		 //now the null and email is checked and completed
		 //so now we will set the full name here
		 contact.setFullName(buildFullName(
				 contact.getFirst_name(),
				 contact.getLast_name()
				 ));
		 
		 //Now we will save the contact to the database
		 return contactRepository.save(contact);
	}
		 
		 //||-------------------GET CONTACT BY ID -------------------||
		 
		 public Contact getContactById(Long id) {
			 //if the id is null or the id is less than 0 then the id is incorrect
			 if(id == null || id <= 0) throw new InvalidInputException("The entered Id is incorrect");
			 
			 Contact contact = contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("The contact is not found")); 
			 
			 if(contact.getDeleted()) throw new ContactNotFoundException("COntact not found");
			 
			 return contact;
		
		
	}
		 
		 //||--------------------------GET ALL THE CONTACTS-----------------------||
		 public List<Contact> getAllContact(){
			 
			 return contactRepository.findAll().stream().filter(contact -> !contact.getDeleted() ).toList();
			 
		 }
		 
		 //||-----------------------UPDATE THE CONTACTS------------------------||
		 
		 public Contact updateContact(Long id, Contact updatecontact) {
			 if(id == null || id <= 0) throw new ContactNotFoundException("Contact is null or invalid");
			 
			 Contact existing = contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact does not exist in the db"));
			 
			 if(existing.getDeleted()) throw new ContactNotFoundException("Cannot update the deleted contact");
			 
			 //here we alllow the limited fileds for the updation 
			 if(updatecontact.getFirst_name() != null) existing.setFirst_name(updatecontact.getFirst_name());
			 
			 if(updatecontact.getLast_name() != null) existing.setLast_name(updatecontact.getLast_name());
			 
			 if(updatecontact.getPhone() != null) existing.setPhone(updatecontact.getPhone());
			 
			 if(updatecontact.getTags() != null) existing.setTags(updatecontact.getTags());
			 
			 //Here now we will also check the email updation means email uniqueness
			 if(updatecontact.getEmail() != null) {
				 String newEmail = updatecontact.getEmail().trim().toLowerCase();
				 
				 if(!newEmail.equals(existing.getEmail())) {
					 Optional<Contact> emailOwner = contactRepository.findByEmail(newEmail);
					 if(emailOwner.isPresent() && !emailOwner.get().getDeleted()) throw new DuplicateEmailException("Email already exists");
					 existing.setEmail(newEmail);
				 }				 
			 }
			 //Now we will rebuild the new mail
			 existing.setFullName(buildFullName(existing.getFirst_name(), existing.getLast_name()));
			 
			 existing.setUpdated_at(LocalDateTime.now());
			 
			 return contactRepository.save(existing);
		 }
		 
		 //||----------------------------------DELETE CONTACT----------------------||
		 
		 public void deleteContact(Long id) {
			 //if the id is null or less tha 0 then we will throw the new illegal argumnet exception error that the contact is empty
			 if(id == null || id <= 0) throw new ContactNotFoundException("Contact is null or empty");
			 
			 Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact is not found"));
			 
			 if(contact.getDeleted()) throw new ContactNotFoundException("contact is already deleted");
			 
			 contact.setDeleted(true);
			 contact.setDeleted_at(LocalDateTime.now());
			 contact.setUpdated_at(LocalDateTime.now());
			 
			 
			 contactRepository.save(contact);
		 }
		 
		 //||---------------------------------HELPER METHOD --------------------------------||
		 
		 private String buildFullName(String firstName, String lastName) {
			 if(lastName == null || lastName.trim().isEmpty()) return firstName;
			 
			 return firstName + " " + lastName;
		 }


		 
		 
	
	
}
