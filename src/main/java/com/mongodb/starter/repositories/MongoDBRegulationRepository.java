package com.mongodb.starter.repositories;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.Regulation;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoClient;
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
public class MongoDBRegulationRepository implements RegulationRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<Regulation> regulationMongoCollection;

    @PostConstruct
    void init(){
        regulationMongoCollection = client.getDatabase("hackcovy").getCollection("regulations", Regulation.class);
    }

    @Override
    public Regulation save(Regulation regulation) {
        regulation.setId(new ObjectId());
        regulationMongoCollection.insertOne(regulation);
        return regulation;
    }

    @Override
    public List<Regulation> saveAll(List<Regulation> regulations) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                regulations.forEach(p -> p.setId(new ObjectId()));
                regulationMongoCollection.insertMany(clientSession, regulations);
                return regulations;
            }, txnOptions);
        }
    }

    @Override
    public List<Regulation> findAll() {
        return regulationMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Regulation> findAll(List<String> ids) {
        return regulationMongoCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public Regulation findOne(String id) {
        return regulationMongoCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return regulationMongoCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return regulationMongoCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> regulationMongoCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> regulationMongoCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Regulation update(Regulation regulation) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return regulationMongoCollection.findOneAndReplace(eq("_id", regulation.getId()), regulation, options);
    }

    @Override
    public long update(List<Regulation> regulations) {
        List<WriteModel<Regulation>> writes = regulations.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> regulationMongoCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
