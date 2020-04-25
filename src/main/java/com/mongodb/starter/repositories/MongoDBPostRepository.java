package com.mongodb.starter.repositories;

import com.mongodb.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.starter.models.Post;
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
public class MongoDBPostRepository implements PostRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    @Autowired
    private MongoClient client;
    private MongoCollection<Post> postMongoCollection;

    @PostConstruct
    void init() {
        postMongoCollection = client.getDatabase("hackcovy").getCollection("posts", Post.class);
    }

    @Override
    public Post save(Post post) {
        post.setId(new ObjectId());
        postMongoCollection.insertOne(post);
        return post;
    }

    @Override
    public List<Post> saveAll(List<Post> posts) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                posts.forEach(p -> p.setId(new ObjectId()));
                postMongoCollection.insertMany(clientSession, posts);
                return posts;
            }, txnOptions);
        }
    }

    @Override
    public List<Post> findAll() {
        return postMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<Post> findAll(List<String> ids) {
        return postMongoCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public Post findOne(String id) {
        return postMongoCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return postMongoCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return postMongoCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> postMongoCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> postMongoCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public Post update(Post ticket) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return postMongoCollection.findOneAndReplace(eq("_id", ticket.getId()), ticket, options);
    }

    @Override
    public long update(List<Post> posts) {
        List<WriteModel<Post>> writes = posts.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()), p))
                .collect(Collectors.toList());
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> postMongoCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }

}
