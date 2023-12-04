package com.example.app.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Review;

@Mapper
public interface ReviewDao {
	
	List<Review> findByPlaceId(String placeId) throws Exception;
	
	Review selectById(Integer id) throws Exception;

	List<Review> selectByUserId(Integer id) throws Exception;
	
	void insert(Review review) throws Exception;
	
	void update(Review review) throws Exception;
	
	void delete(Integer id) throws Exception;

	Collection<Review> findAll() throws Exception;


}
