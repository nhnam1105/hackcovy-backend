package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Guest;

import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends GenericComponentRepository<Guest> {

}
