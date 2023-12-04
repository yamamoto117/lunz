package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RestaurantDao {
	
	Integer findRestaurantIdByPlaceId(@Param("placeId") String placeId) throws Exception;

	void insertRestaurantIfNotExists(@Param("placeId") String placeId) throws Exception;

}