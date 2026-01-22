package com.example.ContactManager.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ContactManager.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
	
	//this will find all the contacts by the email
	Optional<Contact> findByEmail(String email);
	
	//this will list all the contacts which are non deleted
	List<Contact> findByDeletedFalse();
	
	//this will list all the contacts which are non deleted and also the name or email matches the record in db
	List<Contact> findByDeletedFalseAndFullNameContainingOrEmailContaining(String fullName, String email);
	
	
	
}
