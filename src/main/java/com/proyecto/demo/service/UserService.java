package com.proyecto.demo.service;

import java.util.List;

import com.proyecto.demo.domain.User;



public interface UserService {
	
	public void saveUser(User newUser);
	public List<User> getAllUsers();
}
