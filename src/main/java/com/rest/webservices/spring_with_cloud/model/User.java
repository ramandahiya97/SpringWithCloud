package com.rest.webservices.spring_with_cloud.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
	
	private Integer id;
	private String name;
	private LocalDate birthDate;
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
}
