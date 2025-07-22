package com.rest.webservices.inventory_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
	
	private Integer id;
	private String name;
	private LocalDate birthDate;

}
