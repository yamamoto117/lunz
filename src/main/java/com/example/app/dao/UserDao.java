package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.User;

@Mapper
public interface UserDao {

	User selectByLoginId(String loginId) throws Exception;
	
	void insert(User user) throws Exception;

}
