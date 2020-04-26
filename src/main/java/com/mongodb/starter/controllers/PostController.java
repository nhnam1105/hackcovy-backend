package com.mongodb.starter.controllers;

import com.mongodb.starter.models.Post;
import com.mongodb.starter.models.Person;
import com.mongodb.starter.models.QuarantineArea;
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

// POST for Posts
	@PostMapping("quarantine_area/{qaID}/post")
	public ResponseEntity<QuarantineArea> addPost(@PathVariable String qaID, @RequestBody Post post) {
		QuarantineArea quarantineArea = postRepository.save(qaID, post);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}

	@PostMapping("quarantine_area/{qaID}/posts")
	public ResponseEntity<QuarantineArea> addPosts(@PathVariable String qaID, @RequestBody List<Post> posts) {
		QuarantineArea quarantineArea = postRepository.saveAll(qaID, posts);
		if (quarantineArea == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(quarantineArea);
	}
// END POST for Posts

// GET
	@GetMapping("quarantine_area/{qaID}/posts")
	public ResponseEntity<List<Post>> getPosts(@PathVariable String qaID) {
		List<Post> posts = postRepository.findAll(qaID);
		if (posts.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(posts);
	}

	@GetMapping("quarantine_area/{qaID}/post/{id}")
	public ResponseEntity<Post> getPost(@PathVariable String qaID, @PathVariable String id) {
		Post post = postRepository.findOne(qaID, id);
		if (post == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(post);
	}

	@GetMapping("quarantine_area/{qaID}/posts/{ids}")
	public ResponseEntity<List<Post>> getPosts(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		List<Post> posts = postRepository.findAll(qaID, listIds);
		if (posts.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(posts);
	}

	@GetMapping("quarantine_area/{qaID}/posts/count")
	public Long getCount(@PathVariable String qaID) {
		return postRepository.count(qaID);
	}
//END GET

//DELETE
	@DeleteMapping("quarantine_area/{qaID}/post/{id}")
	public Long deletePost(@PathVariable String qaID, @PathVariable String id) {
		return postRepository.delete(qaID, id);
	}

	@DeleteMapping("quarantine_area/{qaID}/posts/{ids}")
	public Long deletePosts(@PathVariable String qaID, @PathVariable String ids) {
		List<String> listIds = asList(ids.split(","));
		return postRepository.delete(qaID, listIds);
	}

	@DeleteMapping("quarantine_area/{qaID}/posts")
	public Long deletePosts(@PathVariable String qaID) {
		return postRepository.deleteAll(qaID);
	}
//END DELETE

//PUT
	@PutMapping("quarantine_area/{qaID}/post")
	public ResponseEntity<Post> putPost(@PathVariable String qaID, @RequestBody Post post) {
		Post result = postRepository.update(qaID, post);
		if (result == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(result);
	}

	@PutMapping("quarantine_area/{qaID}/posts")
	public Long putPost(@PathVariable String qaID, @RequestBody List<Post> posts) {
		return postRepository.update(qaID, posts);
	}
//END PUT

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}

}
