package com.mongodb.starter.repositories;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Contact;

@Repository
public class MongoDBContactRepository extends MongoDBGenericComponentRepository<Contact> implements ContactRepository {

	public MongoDBContactRepository() {
		listName = "contactList";
	}

}
