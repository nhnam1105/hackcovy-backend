package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Guest;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository {

	Guest save(Guest guest);

	List<Guest> saveAll(List<Guest> guests);

	List<Guest> findAll();

	List<Guest> findAll(List<String> ids);

	Guest findOne(String id);

	long count();

	long delete(String id);

	long delete(List<String> ids);

	long deleteAll();

	Guest update(Guest guest);

	long update(List<Guest> guests);

}
