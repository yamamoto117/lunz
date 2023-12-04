package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Review {
	
	private Integer id;
	
	private Integer userId;

	private String placeId;
	
	private Integer restaurantId;

	private String userName;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	@Past
	private Date visitDate;
	
	@NotNull
	private Integer rating;
	
	@Size(max = 1000)
	private String comment;

}
