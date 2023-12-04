package com.example.app.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class User {
	
	private Integer id;
	
	@NotBlank
	@Length(min=6, max=20)
    @Pattern(regexp="^[a-zA-Z][a-zA-Z0-9]*$")
	private String loginId;
	
	@NotBlank
	@Length(min=6, max=20)
	private String loginPass;
	
	@NotBlank
	@Length(min=6, max=20)
	private String loginPassConfirm;
	
	@NotBlank
	@Length(min=1, max=20)
	private String name;

}