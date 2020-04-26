package com.mongodb.starter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Contact;
import com.mongodb.starter.models.QuarantineArea;

@Repository
public class MongoDBContactRepository extends MongoDBGenericComponentRepository<Contact> implements ContactRepository {

	public MongoDBContactRepository() {
		listName = "contactList";
	}

	@Override
	protected List<Contact> getList(QuarantineArea quarantineArea) {
		return quarantineArea.getContactList();
	}

}
