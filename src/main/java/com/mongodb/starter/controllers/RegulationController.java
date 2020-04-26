package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Regulation;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.QuarantineArea;
import com.mongodb.starter.repositories.RegulationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class RegulationController {

	private final static Logger LOGGER = LoggerFactory.getLogger(RegulationController.class);
	private final RegulationRepository regulationRepository;

	public RegulationController(RegulationRepository regulationRepository) {
		this.regulationRepository = regulationRepository;
	}

// POST for Regulations
	@PostMapping("quarantine_area/{qaID}/regulation")
	public ResponseEntity<QuarantineArea> addRegulation(@PathVariable String qaID, @RequestBody Regulation regulation) {
		QuarantineArea quarantineArea = regulationRepository.save(qaID, regulation);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@PostMapping("quarantine_area/{qaID}/regulations")
	public ResponseEntity<QuarantineArea> addRegulations(@PathVariable String qaID, @RequestBody List<Regulation> regulations) {
		QuarantineArea quarantineArea = regulationRepository.saveAll(qaID, regulations);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}
// END POST for Regulations

// GET
	@GetMapping("quarantine_area/{qaID}/regulations")
	public ResponseEntity<List<Regulation>> getRegulations(@PathVariable String qaID) {
		List<Regulation> regulations = regulationRepository.findAll(qaID);
		if (regulations.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(regulations);
	}

	@GetMapping("quarantine_area/{qaID}/regulation/{id}")
	public ResponseEntity<Regulation> getRegulation(@PathVariable String qaID, @PathVariable String id) {
		Regulation regulation = regulationRepository.findOne(qaID, id);
		if (regulation == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(regulation);
	}

	@GetMapping("quarantine_area/{qaID}/regulations/{ids}")
	public ResponseEntity<List<Regulation>> getRegulations(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		List<Regulation> regulations = regulationRepository.findAll(qaID, listIds);
		if (regulations.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(regulations);
	}

	@GetMapping("quarantine_area/{qaID}/regulations/count")
	public Long getCount(@PathVariable String qaID) {
		return regulationRepository.count(qaID);
	}
//END GET

//DELETE
	@DeleteMapping("quarantine_area/{qaID}/regulation/{id}")
	public Long deleteRegulation(@PathVariable String qaID, @PathVariable String id) {
		return regulationRepository.delete(qaID, id);
	}

	@DeleteMapping("quarantine_area/{qaID}/regulations/{ids}")
	public Long deleteRegulations(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return regulationRepository.delete(qaID, listIds);
	}

	@DeleteMapping("quarantine_area/{qaID}/regulations")
	public Long deleteRegulations(@PathVariable String qaID) {
		return regulationRepository.deleteAll(qaID);
	}
//END DELETE

//PUT
	@PutMapping("quarantine_area/{qaID}/regulation")
	public ResponseEntity<Regulation> putRegulation(@PathVariable String qaID, @RequestBody Regulation regulation) {
		Regulation result = regulationRepository.update(qaID, regulation);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(result);
	}

	@PutMapping("quarantine_area/{qaID}/regulations")
	public Long putRegulation(@PathVariable String qaID, @RequestBody List<Regulation> regulations) {
		return regulationRepository.update(qaID, regulations);
	}
//END PUT

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
