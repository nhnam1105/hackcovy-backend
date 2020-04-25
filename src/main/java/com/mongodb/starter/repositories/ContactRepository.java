package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Contact;

import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends GenericComponentRepository<Contact> {

}
