package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository {

    Admin save(Admin admin);

    List<Admin> findAll();

    Admin findOne(String id);

    Admin findOneByUsername(String username);

    long deleteByUsername(String username);

    Boolean checkAuthen(Admin admin);

    long delete(String id);

    Admin update(Admin admin);

    Boolean login (Admin admin);

    void logout (Admin admin);

    Boolean checkActive(Admin admin);

}
