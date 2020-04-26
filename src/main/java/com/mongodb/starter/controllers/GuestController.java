package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Guest;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class GuestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(GuestController.class);
	private final GuestRepository guestRepository;

	public GuestController(GuestRepository guestRepository) {
		this.guestRepository = guestRepository;
	}

// POST for Guests
	@PostMapping("quarantine_area/{qaID}/guest")
	public ResponseEntity<QuarantineArea> addGuest(@PathVariable String qaID, @RequestBody Guest guest) {
		QuarantineArea quarantineArea = guestRepository.save(qaID, guest);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@PostMapping("quarantine_area/{qaID}/guests")
	public ResponseEntity<QuarantineArea> addGuests(@PathVariable String qaID, @RequestBody List<Guest> guests) {
		QuarantineArea quarantineArea = guestRepository.saveAll(qaID, guests);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}
// END POST for Guests

// GET
	@GetMapping("quarantine_area/{qaID}/guests")
	public ResponseEntity<List<Guest>> getGuests(@PathVariable String qaID) {
		List<Guest> guests = guestRepository.findAll(qaID);
		if (guests.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(guests);
	}

	@GetMapping("quarantine_area/{qaID}/guest/{id}")
	public ResponseEntity<Guest> getGuest(@PathVariable String qaID, @PathVariable String id) {
		Guest guest = guestRepository.findOne(qaID, id);
		if (guest == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(guest);
	}

	@GetMapping("quarantine_area/{qaID}/guests/{ids}")
	public ResponseEntity<List<Guest>> getGuests(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		List<Guest> guests = guestRepository.findAll(qaID, listIds);
		if (guests.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(guests);
	}

	@GetMapping("quarantine_area/{qaID}/guests/count")
	public Long getCount(@PathVariable String qaID) {
		return guestRepository.count(qaID);
	}
//END GET

//DELETE
	@DeleteMapping("quarantine_area/{qaID}/guest/{id}")
	public Long deleteGuest(@PathVariable String qaID, @PathVariable String id) {
		return guestRepository.delete(qaID, id);
	}

	@DeleteMapping("quarantine_area/{qaID}/guests/{ids}")
	public Long deleteGuests(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return guestRepository.delete(qaID, listIds);
	}

	@DeleteMapping("quarantine_area/{qaID}/guests")
	public Long deleteGuests(@PathVariable String qaID) {
		return guestRepository.deleteAll(qaID);
	}
//END DELETE

//PUT
	@PutMapping("quarantine_area/{qaID}/guest")
	public ResponseEntity<Guest> putGuest(@PathVariable String qaID, @RequestBody Guest guest) {
		Guest result = guestRepository.update(qaID, guest);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(result);
	}

	@PutMapping("quarantine_area/{qaID}/guests")
	public Long putGuest(@PathVariable String qaID, @RequestBody List<Guest> guests) {
		return guestRepository.update(qaID, guests);
	}
//END PUT

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
