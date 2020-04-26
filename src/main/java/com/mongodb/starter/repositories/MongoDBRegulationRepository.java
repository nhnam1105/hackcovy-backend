package com.mongodb.starter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Regulation;
import com.mongodb.starter.models.QuarantineArea;

@Repository
public class MongoDBRegulationRepository extends MongoDBGenericComponentRepository<Regulation> implements RegulationRepository {

	public MongoDBRegulationRepository() {
		listName = "regulationList";
	}

	@Override
	protected List<Regulation> getList(QuarantineArea quarantineArea) {
		return quarantineArea.getRegulationList();
	}

}
