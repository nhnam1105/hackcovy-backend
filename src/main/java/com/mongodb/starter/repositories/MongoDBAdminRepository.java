package com.mongodb.starter.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.models.Admin;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBAdminRepository implements AdminRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    @Autowired
    private MongoClient client;
    private MongoCollection<Admin> adminCollection;

    @PostConstruct
    void init() {
        adminCollection = client.getDatabase("hackcovy").getCollection("admins", Admin.class);
    }

    @Override
    public Admin save(Admin admin) {
        admin.setId(new ObjectId());
        admin.setPassword(get_SHA_512_SecurePassword(admin.getPassword(), admin.getUsername().getBytes()));
        adminCollection.insertOne(admin);
        return admin;
    }

    @Override
    public List<Admin> findAll() {
        return adminCollection.find().into(new ArrayList<>());
    }

    @Override
    public Admin findOne(String id) {
        return adminCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public Admin findOneByUsername(String username) {
        System.out.println("Username: " + username);
        return adminCollection.find(eq("username", username)).first();
    }

    @Override
    public long delete(String id) {
        return adminCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long deleteByUsername(String username) {
        return adminCollection.deleteOne(eq("username", username)).getDeletedCount();
    }

    @Override
    public Boolean checkAuthen(Admin admin) {
        System.out.println("admin: " + admin.toString());
        Admin ad = findOneByUsername(admin.getUsername());
        System.out.println("ad: " + ad.toString());
        String passwordHash = get_SHA_512_SecurePassword(admin.getPassword(), admin.getUsername().getBytes());
        return ad.getPassword().equals(passwordHash);
    }

    @Override
    public Boolean checkActive(Admin admin) {
        Admin ad = adminCollection.find(eq("username", admin.getUsername())).first();
        return ad.getActive();
    }

    @Override
    public Boolean login (Admin admin){
        if (checkAuthen(admin)){
            admin.setActive(true);
            update(admin);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void logout (Admin admin){
        admin.setActive(false);
        update(admin);
    }

    @Override
    public Admin update(Admin admin) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return adminCollection.findOneAndReplace(eq("_id", admin.getId()), admin, options);
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }

    public String get_SHA_512_SecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
