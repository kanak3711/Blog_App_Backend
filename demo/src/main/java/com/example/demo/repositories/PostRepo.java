package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;

public interface PostRepo  extends JpaRepository<Post,Integer> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category cat);
	List<Post> findByTitleContaining(String title);
}
