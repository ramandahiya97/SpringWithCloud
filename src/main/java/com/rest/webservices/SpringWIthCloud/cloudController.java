package com.rest.webservices.SpringWIthCloud;

import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cloudController {
//	@RequestMapping(method = RequestMethod.GET,path = "/hello")
	@GetMapping(path = "/hello")
	public String hello() {
		return "HELLO    WORLD :)";
	}
}
