package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Guest;
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

	@PostMapping("guest")
	@ResponseStatus(HttpStatus.CREATED)
	public Guest postGuest(@RequestBody Guest guest) {
		return guestRepository.save(guest);
	}

	@PostMapping("guests")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Guest> postGuests(@RequestBody List<Guest> guests) {
		return guestRepository.saveAll(guests);
	}

	@GetMapping("guests")
	public List<Guest> getGuests() {
		return guestRepository.findAll();
	}

	@GetMapping("guest/{id}")
	public ResponseEntity<Guest> getGuest(@PathVariable String id) {
		Guest guest = guestRepository.findOne(id);
		if (guest == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(guest);
	}

	@GetMapping("guests/{ids}")
	public List<Guest> getGuests(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return guestRepository.findAll(listIds);
	}

	@GetMapping("guests/count")
	public Long getCount() {
		return guestRepository.count();
	}

	@DeleteMapping("guest/{id}")
	public Long deleteGuest(@PathVariable String id) {
		return guestRepository.delete(id);
	}

	@DeleteMapping("guests/{ids}")
	public Long deleteGuests(@PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return guestRepository.delete(listIds);
	}

	@DeleteMapping("guests")
	public Long deleteGuests() {
		return guestRepository.deleteAll();
	}

	@PutMapping("guest")
	public Guest putGuest(@RequestBody Guest guest) {
		return guestRepository.update(guest);
	}

	@PutMapping("guests")
	public Long putGuest(@RequestBody List<Guest> guests) {
		return guestRepository.update(guests);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}
}
