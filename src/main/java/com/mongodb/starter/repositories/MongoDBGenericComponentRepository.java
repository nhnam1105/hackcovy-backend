package com.mongodb.starter.repositories;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.model.Projections;
import com.mongodb.starter.dtos.AverageAgeDTO;
import com.mongodb.starter.models.ObjWithID;
import com.mongodb.starter.models.QuarantineArea;

import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.ReturnDocument.AFTER;
import static java.util.Arrays.asList;

@Repository
public abstract class MongoDBGenericComponentRepository<T extends ObjWithID> implements GenericComponentRepository<T> {

	private static final TransactionOptions txnOptions = TransactionOptions.builder()
			.readPreference(ReadPreference.primary()).readConcern(ReadConcern.MAJORITY)
			.writeConcern(WriteConcern.MAJORITY).build();

	@Autowired
	private MongoClient client;
	private MongoCollection<QuarantineArea> quarantineAreaCollection;
	protected String listName;

	@PostConstruct
	void init() {
		quarantineAreaCollection = client.getDatabase("hackcovy").getCollection("quarantine_areas",
				QuarantineArea.class);
	}

	@Override
	public QuarantineArea save(String qaID, T t) {
		t.setId(new ObjectId());
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(AFTER);
		return quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.push(listName, t),
				options);
	}

	@Override
	public QuarantineArea saveAll(String qaID, List<T> ts) {
		ts.forEach(t -> t.setId(new ObjectId()));
		FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(AFTER);
		return quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.push(listName, ts),
				options);
	}

	@Override
	public List<T> findAll(String qaID) {
		return (List<T>) quarantineAreaCollection.find(eq("_id", new ObjectId(qaID))).projection(include(listName))
				.into(new ArrayList<>());
	}

	@Override
	public List<T> findAll(String qaID, List<String> ids) {
		return (List<T>) quarantineAreaCollection
				.find(and(eq("_id", new ObjectId(qaID)), in(listName + "._id", mapToObjectIds(ids))))
				.projection(include(listName)).into(new ArrayList<>());
	}

	@Override
	public T findOne(String qaID, String id) {
		return null; //TODO
	}

	@Override
	public long count(String qaID) {
		return 0; //TODO
	}

	@Override
	public long delete(String qaID, String id) {
		return 0; //TODO
	}

	@Override
	public long delete(String qaID, List<String> ids) {
		return 0; //TODO
	}

	@Override
	public long deleteAll(String qaID) {
		return 0; //TODO
	}

	@Override
	public T update(String qaID, T t) {
		return null; //TODO
	}

	@Override
	public long update(String qaID, List<T> ts) {
		return 0; //TODO
	}

	private List<ObjectId> mapToObjectIds(List<String> ids) {
		return ids.stream().map(ObjectId::new).collect(Collectors.toList());
	}
}
