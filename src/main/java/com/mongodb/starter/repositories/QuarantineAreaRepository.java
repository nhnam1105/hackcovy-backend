package com.mongodb.starter.repositories;

import com.mongodb.starter.models.QuarantineArea;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuarantineAreaRepository {

	QuarantineArea save(QuarantineArea quarantineArea);

	List<QuarantineArea> saveAll(List<QuarantineArea> quarantineAreas);

	List<QuarantineArea> findAll();

	List<QuarantineArea> findAll(List<String> ids);

	QuarantineArea findOne(String id);

	long count();

	long delete(String id);

	long delete(List<String> ids);

	long deleteAll();

	QuarantineArea update(QuarantineArea quarantineArea);

	long update(List<QuarantineArea> quarantineAreas);

}
