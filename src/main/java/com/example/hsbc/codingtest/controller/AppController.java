package com.example.hsbc.codingtest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hsbc.codingtest.dao.UserPostServiceDao;
import com.example.hsbc.codingtest.dao.UserServiceDao;
import com.example.hsbc.codingtest.exception.UserNotFoundException;
import com.example.hsbc.codingtest.exception.UserPostLengthInvalid;
import com.example.hsbc.codingtest.model.User;
import com.example.hsbc.codingtest.model.UserPost;
import com.example.hsbc.codingtest.utility.Utility;

@RestController
public class AppController {

	@Autowired
	private UserServiceDao userServiceDao;

	@Autowired
	UserPostServiceDao userPostServiceDao;

	/*
	 * Used for Testing Uncomment this method and send a post request to create
	 * sample data.
	 * 
	 * @PostMapping("/users/testDataInsert") public void test() { User s1 =
	 * userServiceDao.createUser("s1", "shubham", LocalDateTime.now(),
	 * LocalDateTime.now()); User s2 = userServiceDao.createUser("s2",
	 * "vishnoi", LocalDateTime.now(), LocalDateTime.now());
	 * 
	 * // create a post userPostServiceDao.createUserPost(s1,
	 * "Hello : Post 1 "); userPostServiceDao.createUserPost(s1,
	 * "Hello : Post 2 "); userPostServiceDao.createUserPost(s1,
	 * "Hello : Post 3 "); userPostServiceDao.createUserPost(s2,
	 * "World  : Post 1 ");
	 * 
	 * }
	 */

	/**
	 * @param userName
	 * @return all posts of the user User sorted by lastUpdatedDate
	 */
	@GetMapping("/users/{userName}/postsreverseorder")
	public List<UserPost> getSelfPosts(@PathVariable String userName) {

		// Finding the User
		User user = checkuser(userName);

		List<UserPost> listofPosts = user.getPosts();

		// reverse chronological order
		Utility.reversePosts(listofPosts);

		return listofPosts;
	}

	/**
	 * @param userName
	 * @return all posts of the user User + the followers(the ones that user
	 *         follows posts sorted by lastUpdatedDate
	 */
	@GetMapping("/users/{userName}/timeline")
	public List<UserPost> getAllPosts(@PathVariable String userName) {
		// Finding the User
		User user = checkuser(userName);

		List<UserPost> selfPosts = user.getPosts();
		List<User> listofUsers = user.getFollowing();

		List<UserPost> result = new ArrayList<UserPost>();
		Stream<UserPost> stream = Stream.of();

		for (User u : listofUsers)
			stream = Stream.concat(stream, u.getPosts().stream());

		result = stream.collect(Collectors.toList());

		result.addAll(selfPosts);
		Utility.reversePosts(result);
		return result;

	}

	/**
	 * Create a post
	 * 
	 * @param userName
	 * @return Http Status
	 */
	@PostMapping("/users/{userName}/posts")
	public ResponseEntity<String> createPost(@PathVariable String userName, @RequestBody String post) {

		// message validation for 140 characters

		if (post.length() < 1 || post.length() > 140)
			throw new UserPostLengthInvalid(
					"Post Length is :" + post.length() + " It should be between 1-140 characters");

		// Finding the User
		User user = checkuser(userName);

		// DaoService to save the post
		UserPost p = userPostServiceDao.createUserPost(user, post);

		// TODO Add exception Handling if the resource creation fails
		return new ResponseEntity<>("Post Created Successfully : " + p.getMessage(), HttpStatus.CREATED);

	}

	/**
	 * Follow a User
	 * 
	 * @param userName
	 * @return Http Status
	 */
	@PostMapping("/users/{userFollowing}/follow/{usertoFollow}")
	public ResponseEntity<String> followUser(@PathVariable String userFollowing, @PathVariable String usertoFollow) {

		// Finding the User
		User user_Following = checkuser(userFollowing);
		User user_to_Follow = checkuser(usertoFollow);

		userServiceDao.followUser(user_Following, user_to_Follow);

		return new ResponseEntity<>("User : " + userFollowing + "  started following : " + usertoFollow,
				HttpStatus.CREATED);

	}

	private User checkuser(String userName) throws NoSuchElementException {

		User user = null;
		try {
			user = userServiceDao.findByUserName(userName);
		} catch (NoSuchElementException k) {
			throw new UserNotFoundException("User :" + userName + " not found!!");
		}
		return user;
	}

}
