package com.example.hsbc.codingtest.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "User")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2393109289142424762L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String userName;

	private String displayName;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserPost> posts = new ArrayList<UserPost>();

	@JsonIgnore
	@ManyToMany(mappedBy = "followers", cascade = CascadeType.REFRESH)
	private List<User> following = new ArrayList<User>();

	@ManyToMany
	@JoinTable(name = "Follower_Following_Table", joinColumns = @JoinColumn(name = "user_following"), inverseJoinColumns = @JoinColumn(name = "user_follower"))
	private List<User> followers = new ArrayList<User>();

	@CreationTimestamp
	private LocalDateTime createdDate;

	@UpdateTimestamp
	private LocalDateTime lastUpdatedDate;

	public User() {
	}

	public User(String userName, String displayName, LocalDateTime createdDate, LocalDateTime lastUpdatedDate) {
		this.userName = userName;
		this.displayName = displayName;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<UserPost> getPosts() {
		return posts;
	}

	public void addPost(UserPost post) {
		this.posts.add(post);
	}

	public void addFollowing(User user) {
		this.following.add(user);
	}

	public List<User> getFollowing() {
		return following;
	}

	public void addFollower(User user) {
		this.followers.add(user);
	}

	public List<User> getFollowers() {
		return followers;
	}

}
