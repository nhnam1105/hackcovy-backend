package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Contact;
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

	@PostMapping("contact")
	@ResponseStatus(HttpStatus.CREATED)
	public Contact postContact(@RequestBody Contact contact) {
		return contactRepository.save(contact);
	}

	@PostMapping("contacts")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Contact> postContacts(@RequestBody List<Contact> contacts) {
		return contactRepository.saveAll(contacts);
	}

	@GetMapping("contacts")
	public List<Contact> getContacts() {
		return contactRepository.findAll();
	}

	@GetMapping("contact/{id}")
	public ResponseEntity<Contact> getContact(@PathVariable String id) {
		Contact contact = contactRepository.findOne(id);
		if (contact == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(contact);
	}

	@GetMapping("contacts/{ids}")
	public List<Contact> getContacts(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return contactRepository.findAll(listIds);
	}

	@GetMapping("contacts/count")
	public Long getCount() {
		return contactRepository.count();
	}

	@DeleteMapping("contact/{id}")
	public Long deleteContact(@PathVariable String id) {
		return contactRepository.delete(id);
	}

	@DeleteMapping("contacts/{ids}")
	public Long deleteContacts(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return contactRepository.delete(listIds);
	}

	@DeleteMapping("contacts")
	public Long deleteContacts() {
		return contactRepository.deleteAll();
	}

	@PutMapping("contact")
	public Contact putContact(@RequestBody Contact contact) {
		return contactRepository.update(contact);
	}

	@PutMapping("contacts")
	public Long putContact(@RequestBody List<Contact> contacts) {
		return contactRepository.update(contacts);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}
}
