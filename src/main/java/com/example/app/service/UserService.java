package com.example.app.service;

import java.util.List;

import com.example.app.domain.Review;
import com.example.app.domain.User;

public interface UserService {

	User getUserByLoginId(String logingId) throws Exception;
	
	List<Review> getReviewsByUserId(Integer userId) throws Exception;
	
	void addUser(User user) throws Exception;
}