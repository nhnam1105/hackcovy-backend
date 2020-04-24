package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository {

	Post save(Post post);

	List<Post> saveAll(List<Post> posts);

	List<Post> findAll();

	List<Post> findAll(List<String> ids);

	Post findOne(String id);

	long count();

	long delete(String id);

	long delete(List<String> ids);

	long deleteAll();

	Post update(Post post);

	long update(List<Post> posts);

}
