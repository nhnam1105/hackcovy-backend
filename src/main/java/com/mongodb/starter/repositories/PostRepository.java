package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Post;

import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends GenericComponentRepository<Post> {

}
