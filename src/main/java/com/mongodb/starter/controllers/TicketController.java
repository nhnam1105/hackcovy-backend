package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Ticket;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class TicketController {

	private final static Logger LOGGER = LoggerFactory.getLogger(TicketController.class);
	private final TicketRepository ticketRepository;

	public TicketController(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

// POST for Tickets
	@PostMapping("quarantine_area/{qaID}/ticket")
	public ResponseEntity<QuarantineArea> addTicket(@PathVariable String qaID, @RequestBody Ticket ticket) {
		QuarantineArea quarantineArea = ticketRepository.save(qaID, ticket);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@PostMapping("quarantine_area/{qaID}/tickets")
	public ResponseEntity<QuarantineArea> addTickets(@PathVariable String qaID, @RequestBody List<Ticket> tickets) {
		QuarantineArea quarantineArea = ticketRepository.saveAll(qaID, tickets);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}
// END POST for Tickets

// GET
	@GetMapping("quarantine_area/{qaID}/tickets")
	public ResponseEntity<List<Ticket>> getTickets(@PathVariable String qaID) {
		List<Ticket> tickets = ticketRepository.findAll(qaID);
		if (tickets.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(tickets);
	}

	@GetMapping("quarantine_area/{qaID}/ticket/{id}")
	public ResponseEntity<Ticket> getTicket(@PathVariable String qaID, @PathVariable String id) {
		Ticket ticket = ticketRepository.findOne(qaID, id);
		if (ticket == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(ticket);
	}

	@GetMapping("quarantine_area/{qaID}/tickets/{ids}")
	public ResponseEntity<List<Ticket>> getTickets(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		List<Ticket> tickets = ticketRepository.findAll(qaID, listIds);
		if (tickets.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(tickets);
	}

	@GetMapping("quarantine_area/{qaID}/tickets/count")
	public Long getCount(@PathVariable String qaID) {
		return ticketRepository.count(qaID);
	}
//END GET

//DELETE
	@DeleteMapping("quarantine_area/{qaID}/ticket/{id}")
	public Long deleteTicket(@PathVariable String qaID, @PathVariable String id) {
		return ticketRepository.delete(qaID, id);
	}

	@DeleteMapping("quarantine_area/{qaID}/tickets/{ids}")
	public Long deleteTickets(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return ticketRepository.delete(qaID, listIds);
	}

	@DeleteMapping("quarantine_area/{qaID}/tickets")
	public Long deleteTickets(@PathVariable String qaID) {
		return ticketRepository.deleteAll(qaID);
	}
//END DELETE

//PUT
	@PutMapping("quarantine_area/{qaID}/ticket")
	public ResponseEntity<Ticket> putTicket(@PathVariable String qaID, @RequestBody Ticket ticket) {
		Ticket result = ticketRepository.update(qaID, ticket);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(result);
	}

	@PutMapping("quarantine_area/{qaID}/tickets")
	public Long putTicket(@PathVariable String qaID, @RequestBody List<Ticket> tickets) {
		return ticketRepository.update(qaID, tickets);
	}
//END PUT

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
