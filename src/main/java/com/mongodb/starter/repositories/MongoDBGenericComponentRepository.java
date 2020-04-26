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
import com.mongodb.lang.Nullable;
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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.*;
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

	protected abstract List<T> getList(QuarantineArea quarantineArea);

	private static FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().returnDocument(AFTER);

	@PostConstruct
	void init() {
		quarantineAreaCollection = client.getDatabase("hackcovy").getCollection("quarantine_areas",
				QuarantineArea.class);
	}

	/**
	 * Returns null if qaID not found
	 */
	@Override
	public QuarantineArea save(String qaID, T t) {
		t.setId(new ObjectId());
		return quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.push(listName, t),
				options);
	}

	/**
	 * Returns null if qaID not found
	 */
	@Override
	public QuarantineArea saveAll(String qaID, List<T> ts) {
		ts.forEach(t -> t.setId(new ObjectId()));
		return quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.pushEach(listName, ts),
				options);
	}

	/**
	 * May return empty list
	 */
	@Override
	public List<T> findAll(String qaID) {
		return getList(getQA(qaID));
	}

	/**
	 * May return empty list
	 */
	@Override
	public List<T> findAll(String qaID, List<String> ids) {
		List<T> ts = findAll(qaID);
		if (ts.isEmpty())
			return new ArrayList<T>();
		List<ObjectId> convertedIDs = mapToObjectIds(ids);
		Predicate<T> matchID = t -> convertedIDs.contains(t.getId());
		return ts.stream().filter(matchID).collect(Collectors.toList());
	}

	/**
	 * May return null
	 */
	@Override
	public T findOne(String qaID, String id) {
		List<T> ts = findAll(qaID);
		if (ts.isEmpty())
			return null;
		Predicate<T> matchID = t -> t.getId().toHexString().equals(id);
		return ts.stream().filter(matchID).findAny().orElse(null);
	}

	@Nullable
	private QuarantineArea getQA(String qaID) {
		return quarantineAreaCollection.find(eq("_id", new ObjectId(qaID))).first();
	}

	/**
	 * Return -1 if qaID not found
	 */
	@Override
	public long count(String qaID) {
		QuarantineArea qa = getQA(qaID);
		if (qa == null)
			return -1;
		if (getList(qa) == null)
			return 0;
		return getList(qa).size();
	}

	@Override
	public long delete(String qaID, String id) {
		if (getQA(qaID) == null)
			return 0;
		T item = findOne(qaID, id);
		if (item == null)
			return 0;
		quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.pull(listName, item));
		return 1;
	}

	@Override
	public long delete(String qaID, List<String> ids) {
		if (getQA(qaID) == null)
			return 0;
		List<T> items = findAll(qaID, ids);
		if (items.isEmpty())
			return 0;
		quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.pullAll(listName, items));
		return items.size();
	}

	@Override
	public long deleteAll(String qaID) {
		if (getQA(qaID) == null)
			return 0;
		long count = count(qaID);
		if (count <= 0)
			return 0;
		quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)),
				Updates.set(listName, new ArrayList<T>()));
		return count;
	}

	/**
	 * May return null
	 */
	@Override
	public T update(String qaID, T newT) {
//		QuarantineArea qa = getQA(qaID);
//		if (qa == null)
//			return null;
//		List<T> ts = getList(qa);
//		if (ts == null)
//			return null;
//		for (int i = 0; i < ts.size(); i++) {
//			T t = ts.get(i);
//			if (newT.getId() == null)
//				throw new NullPointerException();
//			if (t.getId().toHexString().equals(newT.getId().toHexString())) {
//				t = newT;
//				quarantineAreaCollection.findOneAndUpdate(eq("_id", new ObjectId(qaID)), Updates.set(listName, ts),
//						options);
//				return newT;
//			}
//		}
		return null;
	}

	@Override
	public long update(String qaID, List<T> ts) {
		return 0; // TODO
	}

	private List<ObjectId> mapToObjectIds(List<String> ids) {
		return ids.stream().map(ObjectId::new).collect(Collectors.toList());
	}
}
