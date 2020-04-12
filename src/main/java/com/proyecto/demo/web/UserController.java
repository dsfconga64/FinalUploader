package com.proyecto.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.proyecto.demo.domain.User;
import com.proyecto.demo.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService serv;
	
	
	@PostMapping("/user")
	public ResponseEntity<String> addUser(@RequestBody User newUser) {
		serv.saveUser(newUser);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
	
}
