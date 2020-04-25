package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Regulation;
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

    @PostMapping("regulation")
    @ResponseStatus(HttpStatus.CREATED)
    public Regulation postRegulation(@RequestBody Regulation regulation) {
        return regulationRepository.save(regulation);
    }

    @PostMapping("regulations")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Regulation> postRegulations(@RequestBody List<Regulation> regulations) {
        return regulationRepository.saveAll(regulations);
    }

    @GetMapping("regulations")
    public List<Regulation> getRegulations() {
        return regulationRepository.findAll();
    }

    @GetMapping("regulation/{id}")
    public ResponseEntity<Regulation> getRegulation(@PathVariable String id) {
        Regulation regulation = regulationRepository.findOne(id);
        if (regulation == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(regulation);
    }

    @GetMapping("regulations/{ids}")
    public List<Regulation> getRegulations(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return regulationRepository.findAll(listIds);
    }

    @GetMapping("regulations/count")
    public Long getCount() {
        return regulationRepository.count();
    }

    @DeleteMapping("regulation/{id}")
    public Long deleteRegulation(@PathVariable String id) {
        return regulationRepository.delete(id);
    }

    @DeleteMapping("regulations/{ids}")
    public Long deleteRegulations(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return regulationRepository.delete(listIds);
    }

    @DeleteMapping("regulations")
    public Long deleteRegulations() {
        return regulationRepository.deleteAll();
    }

    @PutMapping("regulation")
    public Regulation putRegulation(@RequestBody Regulation regulation) {
        return regulationRepository.update(regulation);
    }

    @PutMapping("regulations")
    public Long putRegulation(@RequestBody List<Regulation> regulations) {
        return regulationRepository.update(regulations);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
