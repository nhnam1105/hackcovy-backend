package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.QuarantineArea;

import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBQuarantineAreaRepository implements QuarantineAreaRepository {

	private static final TransactionOptions txnOptions = TransactionOptions.builder()
			.readPreference(ReadPreference.primary()).readConcern(ReadConcern.MAJORITY)
			.writeConcern(WriteConcern.MAJORITY).build();

	@Autowired
	private MongoClient client;
	private MongoCollection<QuarantineArea> quarantineAreaCollection;

	@PostConstruct
	void init() {
		quarantineAreaCollection = client.getDatabase("hackcovy").getCollection("quarantine_areas",
				QuarantineArea.class);
	}

	@Override
	public QuarantineArea save(QuarantineArea quarantineArea) {
		quarantineArea.setId(new ObjectId());
		quarantineAreaCollection.insertOne(quarantineArea);
		return quarantineArea;
	}

	@Override
	public List<QuarantineArea> saveAll(List<QuarantineArea> quarantineAreas) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(() -> {
				quarantineAreas.forEach(ia -> ia.setId(new ObjectId()));
				quarantineAreaCollection.insertMany(clientSession, quarantineAreas);
				return quarantineAreas;
			}, txnOptions);
		}
	}

	@Override
	public List<QuarantineArea> findAll() {
		return quarantineAreaCollection.find().into(new ArrayList<>());
	}

	@Override
	public List<QuarantineArea> findAll(List<String> ids) {
		return quarantineAreaCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
	}

	@Override
	public QuarantineArea findOne(String id) {
		return quarantineAreaCollection.find(eq("_id", new ObjectId(id))).first();
	}

	@Override
	public long count() {
		return quarantineAreaCollection.countDocuments();
	}

	@Override
	public long delete(String id) {
		return quarantineAreaCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
	}

	@Override
	public long delete(List<String> ids) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(() -> quarantineAreaCollection
					.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(), txnOptions);
		}
	}

	@Override
	public long deleteAll() {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> quarantineAreaCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(),
					txnOptions);
		}
	}

	@Override
	public QuarantineArea update(QuarantineArea quarantineArea) {
		FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
		return quarantineAreaCollection.findOneAndReplace(eq("_id", quarantineArea.getId()), quarantineArea, options);
	}

	@Override
	public long update(List<QuarantineArea> quarantineAreas) {
		List<WriteModel<QuarantineArea>> writes = quarantineAreas.stream()
				.map(ia -> new ReplaceOneModel<>(eq("_id", ia.getId()), ia)).collect(Collectors.toList());
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> quarantineAreaCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
		}
	}

	private List<ObjectId> mapToObjectIds(List<String> ids) {
		return ids.stream().map(ObjectId::new).collect(Collectors.toList());
	}
}
