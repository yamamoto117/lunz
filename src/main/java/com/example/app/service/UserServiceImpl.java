package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ReviewDao;
import com.example.app.dao.UserDao;
import com.example.app.domain.Review;
import com.example.app.domain.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;
	
	@Autowired
    ReviewDao reviewDao;

	@Override
	public User getUserByLoginId(String logingId) throws Exception {
		return userdao.selectByLoginId(logingId);
	}
	
	@Override
    public List<Review> getReviewsByUserId(Integer userId) throws Exception {
        return reviewDao.selectByUserId(userId);
    }
	
	@Override
	public void addUser(User user) throws Exception {
		userdao.insert(user);
	}

}
