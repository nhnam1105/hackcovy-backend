package com.mongodb.starter.controllers;

import com.mongodb.starter.models.QuarantineArea;
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

	public QuarantineAreaController(QuarantineAreaRepository quarantineAreaRepository) {
		this.quarantineAreaRepository = quarantineAreaRepository;
	}

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

	@PutMapping("quarantine_area")
	public QuarantineArea putQuarantineArea(@RequestBody QuarantineArea quarantineArea) {
		return quarantineAreaRepository.update(quarantineArea);
	}

	@PutMapping("quarantine_areas")
	public Long putQuarantineArea(@RequestBody List<QuarantineArea> quarantineAreas) {
		return quarantineAreaRepository.update(quarantineAreas);
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}
}
