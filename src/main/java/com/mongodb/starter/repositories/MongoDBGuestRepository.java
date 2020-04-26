package com.mongodb.starter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Guest;
import com.mongodb.starter.models.QuarantineArea;

@Repository
public class MongoDBGuestRepository extends MongoDBGenericComponentRepository<Guest> implements GuestRepository {

	public MongoDBGuestRepository() {
		listName = "guestList";
	}

	@Override
	protected List<Guest> getList(QuarantineArea quarantineArea) {
		return quarantineArea.getGuestList();
	}

}
