package com.crud.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.users.model.User;
import com.crud.users.repository.UserRepository;

@RestController
@RequestMapping({"/users"})
public class UserController {

	private UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public List<User> findAll(){
	   return userRepository.findAll();
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<User> findById(@PathVariable long id){
	   return userRepository.findById(id)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public User create(@RequestBody User contact){
	   return userRepository.save(contact);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<User> update(@PathVariable("id") long id, @RequestBody User user) {
	   return userRepository.findById(id)
	           .map(record -> {
	               record.setNome(user.getNome());
	               record.setEmail(user.getEmail());
	               User updated = userRepository.save(record);
	               return ResponseEntity.ok().body(updated);})
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity<?> delete(@PathVariable long id) {
	   return userRepository.findById(id)
	           .map(record -> {
	        	   userRepository.deleteById(id);
	               return ResponseEntity.ok().build();})
	           .orElse(ResponseEntity.notFound().build());
	}
}