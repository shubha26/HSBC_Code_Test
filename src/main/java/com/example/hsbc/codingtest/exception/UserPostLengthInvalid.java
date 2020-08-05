package com.example.hsbc.codingtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserPostLengthInvalid extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3202159665454575451L;

	public UserPostLengthInvalid(String message) {
		super(message);
	}
}
