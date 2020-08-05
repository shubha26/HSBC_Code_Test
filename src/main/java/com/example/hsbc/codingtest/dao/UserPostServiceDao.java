package com.example.hsbc.codingtest.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.example.hsbc.codingtest.model.User;
import com.example.hsbc.codingtest.model.UserPost;

@Component

public class UserPostServiceDao {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public UserPost createUserPost(User user, String post) {

		UserPost p = new UserPost(post, LocalDateTime.now(), LocalDateTime.now());

		// Setting the relationship (User <-> Post)

		p.setUser(user);

		User u = entityManager.find(User.class, user.getId());
		u.addPost(p);
		// entityManager.persist(user);
		entityManager.persist(p);
		return p;
	}

}
