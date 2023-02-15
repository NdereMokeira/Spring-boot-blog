package com.springboot.simple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.simple.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByTitleContaining(String title);
}
