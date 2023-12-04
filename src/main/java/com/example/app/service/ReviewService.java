package com.example.app.service;

import java.util.List;
import java.util.Map;

import com.example.app.domain.Review;

public interface ReviewService {

	
	List<Review> getReviewsByPlaceId(String placeId) throws Exception;

	Review getReviewById(Integer id) throws Exception;

	void addReview(Review review) throws Exception;

	void editReview(Review review) throws Exception;
	
	void deleteReview(Integer id) throws Exception;
	
	Map<String, Double> getAverageRatings() throws Exception;
	
    Map<String, Long> getReviewCounts() throws Exception;

}
