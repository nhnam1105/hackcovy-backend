package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Contact;
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
	@ResponseStatus(HttpStatus.CREATED)
	public QuarantineArea addContact(@PathVariable String qaID, @RequestBody Contact contact) {
		return contactRepository.save(qaID, contact);
	}

	@PostMapping("quarantine_area/{qaID}/contacts")
	@ResponseStatus(HttpStatus.CREATED)
	public QuarantineArea addContacts(@PathVariable String qaID, @RequestBody List<Contact> contacts) {
		return contactRepository.saveAll(qaID, contacts);
	}
	// END POST for Contacts

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
