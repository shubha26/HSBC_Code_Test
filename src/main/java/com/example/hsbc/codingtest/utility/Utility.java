package com.example.hsbc.codingtest.utility;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.hsbc.codingtest.model.UserPost;

public class Utility {

	public static void reversePosts(List<UserPost> listofPosts) {

		Comparator<UserPost> reverseComparator = Comparator.comparing(UserPost::getLastUpdatedDate);
		Collections.sort(listofPosts, reverseComparator.reversed());

/*
 * Collections.sort(listofPosts, (p1, p2) ->p1.getLastUpdatedDate().compareTo(p2.getLastUpdatedDate()));
 * Collections.reverse(listofPosts);
 */

	}
}
