package com.mongodb.starter.repositories;

import com.mongodb.starter.models.ObjWithID;
import com.mongodb.starter.models.QuarantineArea;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenericComponentRepository<T extends ObjWithID> {

	QuarantineArea save(String qaID, T t);

	QuarantineArea saveAll(String qaID, List<T> ts);

	List<T> findAll(String qaID);

	List<T> findAll(String qaID, List<String> ids);

	T findOne(String qaID, String id);

	long count(String qaID);

	long delete(String qaID, String id);

	long delete(String qaID, List<String> ids);

	long deleteAll(String qaID);

	T update(String qaID, T t);

	long update(String qaID, List<T> ts);

}
