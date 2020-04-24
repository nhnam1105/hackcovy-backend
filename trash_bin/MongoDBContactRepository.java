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
import com.mongodb.starter.models.Contact;
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
public class MongoDBContactRepository implements ContactRepository {

	private static final TransactionOptions txnOptions = TransactionOptions.builder()
			.readPreference(ReadPreference.primary()).readConcern(ReadConcern.MAJORITY)
			.writeConcern(WriteConcern.MAJORITY).build();

	@Autowired
	private MongoClient client;
	private MongoCollection<Contact> contactCollection;

	@PostConstruct
	void init() {
		contactCollection = client.getDatabase("hackcovy").getCollection("contacts", Contact.class);
	}

	@Override
	public Contact save(Contact contact) {
		contact.setId(new ObjectId());
		contactCollection.insertOne(contact);
		return contact;
	}

	@Override
	public List<Contact> saveAll(List<Contact> contacts) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(() -> {
				contacts.forEach(c -> c.setId(new ObjectId()));
				contactCollection.insertMany(clientSession, contacts);
				return contacts;
			}, txnOptions);
		}
	}

	@Override
	public List<Contact> findAll() {
		return contactCollection.find().into(new ArrayList<>());
	}

	@Override
	public List<Contact> findAll(List<String> ids) {
		return contactCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
	}

	@Override
	public Contact findOne(String id) {
		return contactCollection.find(eq("_id", new ObjectId(id))).first();
	}

	@Override
	public long count() {
		return contactCollection.countDocuments();
	}

	@Override
	public long delete(String id) {
		return contactCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
	}

	@Override
	public long delete(List<String> ids) {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> contactCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
					txnOptions);
		}
	}

	@Override
	public long deleteAll() {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> contactCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(),
					txnOptions);
		}
	}

	@Override
	public Contact update(Contact contact) {
		FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
		return contactCollection.findOneAndReplace(eq("_id", contact.getId()), contact, options);
	}

	@Override
	public long update(List<Contact> contacts) {
		List<WriteModel<Contact>> writes = contacts.stream().map(c -> new ReplaceOneModel<>(eq("_id", c.getId()), c))
				.collect(Collectors.toList());
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> contactCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
		}
	}

	private List<ObjectId> mapToObjectIds(List<String> ids) {
		return ids.stream().map(ObjectId::new).collect(Collectors.toList());
	}
}
