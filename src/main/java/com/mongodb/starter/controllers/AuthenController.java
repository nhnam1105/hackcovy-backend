package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Admin;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.repositories.AdminRepository;
import com.mongodb.starter.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authen")
public class AuthenController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenController.class);
    private final AdminRepository adminRepository;

    public AuthenController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public Boolean login(@RequestBody Admin admin) {
      return adminRepository.login(admin);
    }

    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public Boolean logout(@RequestBody Admin admin) {
        return adminRepository.login(admin);
    }

    @PostMapping("admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin postPerson(@RequestBody Admin admin) {
        return adminRepository.save(admin);
    }

}
