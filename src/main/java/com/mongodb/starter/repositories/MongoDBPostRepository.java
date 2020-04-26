package com.mongodb.starter.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.models.Post;
import com.mongodb.starter.models.QuarantineArea;

@Repository
public class MongoDBPostRepository extends MongoDBGenericComponentRepository<Post> implements PostRepository {

	public MongoDBPostRepository() {
		listName = "postList";
	}

	@Override
	protected List<Post> getList(QuarantineArea quarantineArea) {
		return quarantineArea.getPostList();
	}

}
