package com.springboot.simple.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.simple.model.Post;
import com.springboot.simple.repository.PostRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(path = "/post")
public class MainController {
	@Autowired
  private PostRepository postRepository;
	@GetMapping("/posts")
	  public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
	    try {
	      List<Post> posts = new ArrayList<Post>();

	      if (title == null)
	        postRepository.findAll().forEach(posts::add);
	      else
	        postRepository.findByTitleContaining(title).forEach(posts::add);

	      if (posts.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(posts, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 @GetMapping("/posts/{id}")
	  public ResponseEntity<Post> getTutorialById(@PathVariable("id") int id) {
	    Optional<Post> postData = postRepository.findById(id);

	    if (postData.isPresent()) {
	      return new ResponseEntity<>(postData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	
	 @PostMapping("/posts")
	  public ResponseEntity<Post> createPost(@RequestBody Post post) {
	    try {
	      Post _post = postRepository
	          .save(new Post(0, post.getTitle(), post.getContent()));
	      return new ResponseEntity<>(_post, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	 
	 @PutMapping("/posts/{id}")
	  public ResponseEntity<Post> updatePost(@PathVariable("id") int id, @RequestBody Post post) {
	    Optional<Post> postData = postRepository.findById(id);

	    if (postData.isPresent()) {
	      Post _post = postData.get();
	      _post.setTitle(post.getTitle());
	      _post.setContent(post.getContent());
	      return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	 
	 @DeleteMapping("/posts/{id}")
	  public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") long id) {
	    try {
	      postRepository.deleteById((int)id);
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	 
	 @DeleteMapping("/posts")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    try {
	      postRepository.deleteAll();
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	  }
}
