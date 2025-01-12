package com.app.rakbank.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.rakbank.dto.UserDto;
import com.app.rakbank.entity.User;
import com.app.rakbank.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
	@Operation(summary = "Get all users", description = "Fetches all users from the database.")
	public List<User> getUsers() {
		return userService.getAll();
	}

    @PostMapping("/")
	@Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
	public User save(@RequestBody User user) {
		return userService.save(user);
	}
	
	
	@PutMapping("/{id}")
	@Operation(summary = "Update user", description = "Updates a user's details using their unique ID.")
	public ResponseEntity<?> updateEmp(@PathVariable Integer id, @RequestBody UserDto user){
		User updateUser = userService.update(user, id);
		
		return  ResponseEntity.ok(userService.save(updateUser));
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Change Password", description = "Update users Password.")
	public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody String password){
		userService.changePassword(id, password);
		Map<String,Boolean> map=new LinkedHashMap<String,Boolean>();
		map.put("passwordChanged", Boolean.TRUE);
		return  ResponseEntity.ok(map);
	}

    @GetMapping("/{id}")
	@Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID.")
	public ResponseEntity<?> getEmpById(@PathVariable Integer id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user", description = "Deletes a user by their unique ID.")
	public ResponseEntity<?> deleteEmp(@PathVariable Integer id){
		Map<String,Boolean> map=new LinkedHashMap<String,Boolean>();
		userService.delete(id);
		map.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(map);
	}
}
