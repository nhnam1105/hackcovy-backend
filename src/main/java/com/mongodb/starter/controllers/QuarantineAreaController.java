package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Contact;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.ContactRepository;
import com.mongodb.starter.repositories.QuarantineAreaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class QuarantineAreaController {

	private final static Logger LOGGER = LoggerFactory.getLogger(QuarantineAreaController.class);
	private final QuarantineAreaRepository quarantineAreaRepository;
//	private final ContactRepository contactRepository;

	public QuarantineAreaController(QuarantineAreaRepository quarantineAreaRepository) {
		this.quarantineAreaRepository = quarantineAreaRepository;
//		this.contactRepository = contactRepository;
	}

//POST for QA
	@PostMapping("quarantine_area")
	@ResponseStatus(HttpStatus.CREATED)
	public QuarantineArea postQuarantineArea(@RequestBody QuarantineArea quarantineArea) {
		return quarantineAreaRepository.save(quarantineArea);
	}

	@PostMapping("quarantine_areas")
	@ResponseStatus(HttpStatus.CREATED)
	public List<QuarantineArea> postQuarantineAreas(@RequestBody List<QuarantineArea> quarantineAreas) {
		return quarantineAreaRepository.saveAll(quarantineAreas);
	}
//END POST for QA


	// TODO: add guest
	// TODO: add guests

	// TODO: add post
	// TODO: add posts

	// TODO: add regulation
	// TODO: add regulations

	// TODO: add ticket
	// TODO: add tickets

//GET for QA
	@GetMapping("quarantine_areas")
	public List<QuarantineArea> getQuarantineAreas() {
		return quarantineAreaRepository.findAll();
	}

	@GetMapping("quarantine_area/{id}")
	public ResponseEntity<QuarantineArea> getQuarantineArea(@PathVariable String id) {
		QuarantineArea quarantineArea = quarantineAreaRepository.findOne(id);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@GetMapping("quarantine_areas/{ids}")
	public List<QuarantineArea> getQuarantineAreas(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return quarantineAreaRepository.findAll(listIds);
	}

	@GetMapping("quarantine_areas/count")
	public Long getCount() {
		return quarantineAreaRepository.count();
	}
//END GET for QA

////GET for Contacts	
//	@GetMapping("quarantine_area/{QAid}/contacts")
//	public ResponseEntity<List<Contact>> getContacts(@PathVariable String QAid) {
//		QuarantineArea quarantineArea = quarantineAreaRepository.findOne(QAid);
//		if (quarantineArea == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.ok(quarantineArea.getContactManager().findAll());
//	}
//
//	@GetMapping("quarantine_area/{QAid}/contact/{Cid}")
//	public ResponseEntity<Contact> getContact(@PathVariable String QAid, @PathVariable String Cid) {
//		QuarantineArea quarantineArea = quarantineAreaRepository.findOne(QAid);
//		if (quarantineArea == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		Contact contact = quarantineArea.getContactManager().findOne(Cid);
//		if (contact == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.ok(contact);
//	}
//
//	@GetMapping("quarantine_area/{QAid}/contacts/{Cids}")
//	public ResponseEntity<List<Contact>> getContacts(@PathVariable String QAid, @PathVariable String Cids) {
//		QuarantineArea quarantineArea = quarantineAreaRepository.findOne(QAid);
//		if (quarantineArea == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		List<String> listIds = asList(Cids.split(","));
//		List<Contact> result = quarantineArea.getContactManager().findAll(listIds);
//		if (result == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.ok(result);
//	}
//
//	@GetMapping("quarantine_area/{QAid}/contacts/count")
//	public ResponseEntity<Long> getContactCount(@PathVariable String QAid) {
//		QuarantineArea quarantineArea = quarantineAreaRepository.findOne(QAid);
//		if (quarantineArea == null)
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		return ResponseEntity.ok(quarantineArea.getContactManager().count());
//	}
////END GET for Contacts

	// TODO: get guests
	// TODO: get guest with certain ID
	// TODO: get guests with certain IDs
	// TODO: get guests count

	// TODO: get posts
	// TODO: get post with certain ID
	// TODO: get posts with certain IDs
	// TODO: get posts count

	// TODO: get regulations
	// TODO: get regulation with certain ID
	// TODO: get regulations with certain IDs
	// TODO: get regulations count

	// TODO: get tickets
	// TODO: get ticket with certain ID
	// TODO: get tickets with certain IDs
	// TODO: get tickets count

	@DeleteMapping("quarantine_area/{id}")
	public Long deleteQuarantineArea(@PathVariable String id) {
		return quarantineAreaRepository.delete(id);
	}

	@DeleteMapping("quarantine_areas/{ids}")
	public Long deleteQuarantineAreas(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return quarantineAreaRepository.delete(listIds);
	}

	@DeleteMapping("quarantine_areas")
	public Long deleteQuarantineAreas() {
		return quarantineAreaRepository.deleteAll();
	}

	// TODO: delete contact with ID
	// TODO: delete contacts with IDs
	// TODO: delete all contacts

	// TODO: delete guest with ID
	// TODO: delete guests with IDs
	// TODO: delete all guests

	// TODO: delete post with ID
	// TODO: delete posts with IDs
	// TODO: delete all posts

	// TODO: delete regulation with ID
	// TODO: delete regulations with IDs
	// TODO: delete all regulations

	// TODO: delete ticket with ID
	// TODO: delete tickets with IDs
	// TODO: delete all tickets

	@PutMapping("quarantine_area")
	public QuarantineArea putQuarantineArea(@RequestBody QuarantineArea quarantineArea) {
		return quarantineAreaRepository.update(quarantineArea);
	}

	@PutMapping("quarantine_areas")
	public Long putQuarantineArea(@RequestBody List<QuarantineArea> quarantineAreas) {
		return quarantineAreaRepository.update(quarantineAreas);
	}

	// TODO: update contact
	// TODO: update contacts

	// TODO: update guest
	// TODO: update guests

	// TODO: update post
	// TODO: update posts

	// TODO: update regulation
	// TODO: update regulations

	// TODO: update ticket
	// TODO: update tickets

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}
}
