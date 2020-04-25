package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Post;
import com.mongodb.starter.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class PostController {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("post")
    @ResponseStatus(HttpStatus.CREATED)
    public Post postPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PostMapping("posts")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Post> postPosts(@RequestBody List<Post> posts) {
        return postRepository.saveAll(posts);
    }

    @GetMapping("posts")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = postRepository.findOne(id);
        if (post == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(post);
    }

    @GetMapping("posts/{ids}")
    public List<Post> getPosts(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return postRepository.findAll(listIds);
    }

    @GetMapping("posts/count")
    public Long getCount() {
        return postRepository.count();
    }

    @DeleteMapping("post/{id}")
    public Long deletePost(@PathVariable String id) {
        return postRepository.delete(id);
    }

    @DeleteMapping("posts/{ids}")
    public Long deletePosts(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return postRepository.delete(listIds);
    }

    @DeleteMapping("posts")
    public Long deletePosts() {
        return postRepository.deleteAll();
    }

    @PutMapping("post")
    public Post putPost(@RequestBody Post post) {
        return postRepository.update(post);
    }

    @PutMapping("posts")
    public Long putPost(@RequestBody List<Post> posts) {
        return postRepository.update(posts);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
