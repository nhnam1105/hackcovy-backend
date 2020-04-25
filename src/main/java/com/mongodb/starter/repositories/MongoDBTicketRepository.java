package com.mongodb.starter.repositories;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.Ticket;
import org.bson.BsonDocument;
import com.mongodb.client.MongoClient;
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
public class MongoDBTicketRepository implements TicketRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<Ticket> ticketMongoCollection;

    @PostConstruct
    void init(){
        ticketMongoCollection = client.getDatabase("hackcovy").getCollection("tickets", Ticket.class);
    }

    @Override
    public Ticket save(Ticket ticket) {
        ticket.setId(new ObjectId());
        ticketMongoCollection.insertOne(ticket);
        return ticket;
    }

    @Override
    public List<Ticket> saveAll(List<Ticket> tickets) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                tickets.forEach(p -> p.setId(new ObjectId()));
                ticketMongoCollection.insertMany(clientSession, tickets);
                return tickets;
            }, txnOptions);
        }
    }

    @Override
    public List<Ticket> findAll() {
        return ticketMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Ticket> findAll(List<String> ids) {
        return ticketMongoCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public Ticket findOne(String id) {
        return ticketMongoCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return ticketMongoCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return ticketMongoCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> ticketMongoCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> ticketMongoCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Ticket update(Ticket ticket) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return ticketMongoCollection.findOneAndReplace(eq("_id", ticket.getId()), ticket, options);
    }

    @Override
    public long update(List<Ticket> tickets) {
        List<WriteModel<Ticket>> writes = tickets.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> ticketMongoCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
