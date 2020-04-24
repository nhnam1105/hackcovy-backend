package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Contact;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository {

	Contact save(Contact contact);

	List<Contact> saveAll(List<Contact> contacts);

	List<Contact> findAll();

	List<Contact> findAll(List<String> ids);

	Contact findOne(String id);

	long count();

	long delete(String id);

	long delete(List<String> ids);

	long deleteAll();

	Contact update(Contact contact);

	long update(List<Contact> contacts);

}
