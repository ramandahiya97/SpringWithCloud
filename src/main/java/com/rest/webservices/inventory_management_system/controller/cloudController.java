package com.rest.webservices.inventory_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cloudController {
//	@RequestMapping(method = RequestMethod.GET,path = "/hello")
	@GetMapping(path = "/hello")
	public String hello() {
		return "HELLO    WORLD :)";
	}
}
