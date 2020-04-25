package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Regulation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegulationRepository {
	Regulation save(Regulation regulation);

	List<Regulation> saveAll(List<Regulation> regulations);

	List<Regulation> findAll();

	List<Regulation> findAll(List<String> ids);

	Regulation findOne(String id);

	long count();

	long delete(String id);

	long delete(List<String> ids);

	long deleteAll();

	Regulation update(Regulation regulation);

	long update(List<Regulation> regulations);

}
