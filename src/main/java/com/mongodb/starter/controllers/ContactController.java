package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Contact;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class ContactController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
	private final ContactRepository contactRepository;

	public ContactController(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

// POST for Contacts
	@PostMapping("quarantine_area/{qaID}/contact")
	public ResponseEntity<QuarantineArea> addContact(@PathVariable String qaID, @RequestBody Contact contact) {
		QuarantineArea quarantineArea = contactRepository.save(qaID, contact);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@PostMapping("quarantine_area/{qaID}/contacts")
	public ResponseEntity<QuarantineArea> addContacts(@PathVariable String qaID, @RequestBody List<Contact> contacts) {
		QuarantineArea quarantineArea = contactRepository.saveAll(qaID, contacts);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}
// END POST for Contacts

// GET
	@GetMapping("quarantine_area/{qaID}/contacts")
	public ResponseEntity<List<Contact>> getContacts(@PathVariable String qaID) {
		List<Contact> contacts = contactRepository.findAll(qaID);
		if (contacts.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(contacts);
	}

	@GetMapping("quarantine_area/{qaID}/contact/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable String qaID, @PathVariable String id) {
		Contact contact = contactRepository.findOne(qaID, id);
		if (contact == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(contact);
	}

	@GetMapping("quarantine_area/{qaID}/contacts/{ids}")
	public ResponseEntity<List<Contact>> getContacts(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		List<Contact> contacts = contactRepository.findAll(qaID, listIds);
		if (contacts.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(contacts);
	}

	@GetMapping("quarantine_area/{qaID}/contacts/count")
	public Long getCount(@PathVariable String qaID) {
		return contactRepository.count(qaID);
	}
//END GET

//DELETE
	@DeleteMapping("quarantine_area/{qaID}/contact/{id}")
	public Long deleteContact(@PathVariable String qaID, @PathVariable String id) {
		return contactRepository.delete(qaID, id);
	}

	@DeleteMapping("quarantine_area/{qaID}/contacts/{ids}")
	public Long deleteContacts(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return contactRepository.delete(qaID, listIds);
	}

	@DeleteMapping("quarantine_area/{qaID}/contacts")
	public Long deleteContacts(@PathVariable String qaID) {
		return contactRepository.deleteAll(qaID);
	}
//END DELETE

//PUT
	@PutMapping("quarantine_area/{qaID}/contact")
	public ResponseEntity<Contact> putContact(@PathVariable String qaID, @RequestBody Contact contact) {
		Contact result = contactRepository.update(qaID, contact);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(result);
	}

	@PutMapping("quarantine_area/{qaID}/contacts")
	public Long putContact(@PathVariable String qaID, @RequestBody List<Contact> contacts) {
		return contactRepository.update(qaID, contacts);
	}
//END PUT

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
