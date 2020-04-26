package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Ticket;

import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends GenericComponentRepository<Ticket> {

}
