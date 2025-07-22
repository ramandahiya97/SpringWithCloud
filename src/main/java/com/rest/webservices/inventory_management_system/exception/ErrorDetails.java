package com.rest.webservices.inventory_management_system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ErrorDetails {
	private final LocalDate timeStamp;
	private final String message;
	private final String details;
}
