package com.example.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@GetMapping("/hello")
	public String Hello() {
		return "Hello";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String userEndPoint(){
		return "Hello. user!!!";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String adminEndPoint(){
		return "Hello. Admin!!!";
	}
}
