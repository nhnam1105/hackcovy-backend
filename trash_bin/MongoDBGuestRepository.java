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
import com.mongodb.starter.models.Guest;
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
public class MongoDBGuestRepository implements GuestRepository {

	private static final TransactionOptions txnOptions = TransactionOptions.builder()
			.readPreference(ReadPreference.primary()).readConcern(ReadConcern.MAJORITY)
			.writeConcern(WriteConcern.MAJORITY).build();

	@Autowired
	private MongoClient client;
	private MongoCollection<Guest> guestCollection;

	@PostConstruct
	void init() {
		guestCollection = client.getDatabase("hackcovy").getCollection("guests", Guest.class);
	}

	@Override
	public Guest save(Guest guest) {
		guest.setId(new ObjectId());
		guestCollection.insertOne(guest);
		return guest;
	}

	@Override
	public List<Guest> saveAll(List<Guest> guests) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(() -> {
				guests.forEach(g -> g.setId(new ObjectId()));
				guestCollection.insertMany(clientSession, guests);
				return guests;
			}, txnOptions);
		}
	}

	@Override
	public List<Guest> findAll() {
		return guestCollection.find().into(new ArrayList<>());
	}

	@Override
	public List<Guest> findAll(List<String> ids) {
		return guestCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
	}

	@Override
	public Guest findOne(String id) {
		return guestCollection.find(eq("_id", new ObjectId(id))).first();
	}

	@Override
	public long count() {
		return guestCollection.countDocuments();
	}

	@Override
	public long delete(String id) {
		return guestCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
	}

	@Override
	public long delete(List<String> ids) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> guestCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
					txnOptions);
		}
	}

	@Override
	public long deleteAll() {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> guestCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
		}
	}

	@Override
	public Guest update(Guest guest) {
		FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
		return guestCollection.findOneAndReplace(eq("_id", guest.getId()), guest, options);
	}

	@Override
	public long update(List<Guest> guests) {
		List<WriteModel<Guest>> writes = guests.stream().map(g -> new ReplaceOneModel<>(eq("_id", g.getId()), g))
				.collect(Collectors.toList());
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> guestCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
		}
	}

	private List<ObjectId> mapToObjectIds(List<String> ids) {
		return ids.stream().map(ObjectId::new).collect(Collectors.toList());
	}
}
