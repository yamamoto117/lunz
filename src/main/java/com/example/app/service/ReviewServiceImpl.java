package com.example.app.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.RestaurantDao;
import com.example.app.dao.ReviewDao;
import com.example.app.domain.Review;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDao reviewDao;

	@Autowired
	RestaurantDao restaurantDao;

	@Override
	public List<Review> getReviewsByPlaceId(String placeId) throws Exception {
		return reviewDao.findByPlaceId(placeId);
	}

	@Override
	public Review getReviewById(Integer id) throws Exception {
		return reviewDao.selectById(id);
	}

	@Override
	public void addReview(Review review) throws Exception {
		Integer restaurantId = restaurantDao.findRestaurantIdByPlaceId(review.getPlaceId());
		if (restaurantId == null) {
			restaurantDao.insertRestaurantIfNotExists(review.getPlaceId());
			restaurantId = restaurantDao.findRestaurantIdByPlaceId(review.getPlaceId());
		}

		review.setRestaurantId(restaurantId);

		reviewDao.insert(review);
	}

	@Override
	public void editReview(Review review) throws Exception {
		reviewDao.update(review);
	}

	@Override
	public void deleteReview(Integer id) throws Exception {
		reviewDao.delete(id);
	}

	@Override
	public Map<String, Double> getAverageRatings() throws Exception {
		return reviewDao.findAll().stream()
		        .filter(review -> review.getPlaceId() != null)
		        .collect(Collectors.groupingBy(Review::getPlaceId, Collectors.averagingDouble(Review::getRating)));
	}

	@Override
	public Map<String, Long> getReviewCounts() throws Exception {
	    return reviewDao.findAll().stream()
	            .filter(review -> review.getPlaceId() != null)
	            .collect(Collectors.groupingBy(Review::getPlaceId, Collectors.counting()));
	}
}
