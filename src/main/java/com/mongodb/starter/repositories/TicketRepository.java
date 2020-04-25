package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository {

    Ticket save(Ticket ticket);

    List<Ticket> saveAll(List<Ticket> tickets);

    List<Ticket> findAll();

    List<Ticket> findAll(List<String> ids);

    Ticket findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    Ticket update(Ticket ticket);

    long update(List<Ticket> tickets);

}
