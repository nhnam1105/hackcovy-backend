package com.mongodb.starter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Ticket;
import com.mongodb.starter.models.QuarantineArea;

@Repository
public class MongoDBTicketRepository extends MongoDBGenericComponentRepository<Ticket> implements TicketRepository {

	public MongoDBTicketRepository() {
		listName = "ticketList";
	}

	@Override
	protected List<Ticket> getList(QuarantineArea quarantineArea) {
		return quarantineArea.getTicketList();
	}

}
