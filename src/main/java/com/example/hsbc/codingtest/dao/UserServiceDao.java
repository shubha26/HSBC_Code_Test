package com.example.hsbc.codingtest.dao;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.example.hsbc.codingtest.model.User;

@Component
public class UserServiceDao {

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * TODO 1. Create a user
	 * 
	 */
	@Transactional
	public User createUser(String userName, String displayName, LocalDateTime createdDate,
			LocalDateTime lastUpdatedDate) {
		User user = new User(userName, displayName, createdDate, lastUpdatedDate);
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	@Transactional
	public User findByUserName(String userName) throws NoSuchElementException {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :userName",
				User.class);
		User u = query.setParameter("userName", userName).getSingleResult();
		if (u == null)
			throw new NoSuchElementException();

		return u;
	}

	@Transactional
	public void followUser(User userFollowing, User usertoFollow) {

		User u1 = entityManager.find(User.class, userFollowing.getId());
		User u2 = entityManager.find(User.class, usertoFollow.getId());
		u1.addFollowing(u2);
		u2.addFollower(u1);

	}

}
