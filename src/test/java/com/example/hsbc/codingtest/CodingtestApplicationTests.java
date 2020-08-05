package com.example.hsbc.codingtest;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.hsbc.codingtest.controller.AppController;
import com.example.hsbc.codingtest.dao.UserPostServiceDao;
import com.example.hsbc.codingtest.dao.UserServiceDao;
import com.example.hsbc.codingtest.model.User;
import com.example.hsbc.codingtest.model.UserPost;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodingtestApplication.class)
public class CodingtestApplicationTests {

	
	@Autowired
	UserServiceDao userServiceDao;

	@Autowired
	UserPostServiceDao userPostServiceDao;

	@Autowired
	AppController appController;

	@Test
	@Transactional
	public void contextLoads() {
		
		User s1 = userServiceDao.createUser("s1", "shubham", LocalDateTime.now(), LocalDateTime.now());
		User s2 = userServiceDao.createUser("s2", "vishnoi", LocalDateTime.now(), LocalDateTime.now());

		// create a post
		userPostServiceDao.createUserPost(s1, "Hello : Post 1 ");
		userPostServiceDao.createUserPost(s1, "Hello : Post 2 ");
		userPostServiceDao.createUserPost(s1, "Hello : Post 3 ");
		userPostServiceDao.createUserPost(s2, "World  : Post 1 ");

		// List<UserPost> listofPosts =
		// userServiceDao.findByUserName("s1").getPosts();

		// 1. getSelfposts
		List<UserPost> listofPosts = appController.getSelfPosts("s1");

		// 2. create a User
		appController.createPost("s2", "World  : Post 2 ");

		// 3. follow a user
		appController.followUser("s1", "s2");

		// 4. Getall posts(include posts from followers as well)
		appController.getAllPosts("s1");

	}

}
