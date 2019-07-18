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

import com.crud.users.model.Users;
import com.crud.users.repository.UsersRepository;

@RestController
@RequestMapping({"/users"})
public class UsersController {

	private UsersRepository userRepository;
	
	public UsersController(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public List<Users> findAll(){
	   return userRepository.findAll();
	}
	
	@GetMapping(path = {"/{id}"})
	public ResponseEntity<Users> findById(@PathVariable long id){
	   return userRepository.findById(id)
	           .map(record -> ResponseEntity.ok().body(record))
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Users create(@RequestBody Users contact){
	   return userRepository.save(contact);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Users> update(@PathVariable("id") long id, @RequestBody Users user) {
	   return userRepository.findById(id)
	           .map(record -> {
	               record.setNome(user.getNome());
	               record.setEmail(user.getEmail());
	               Users updated = userRepository.save(record);
	               return ResponseEntity.ok().body(updated);})
	           .orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(path ={"/{id}"})
	public ResponseEntity<Object> delete(@PathVariable long id) {
	   return userRepository.findById(id)
	           .map(record -> {
	        	   userRepository.deleteById(id);
	               return ResponseEntity.ok().build();})
	           .orElse(ResponseEntity.notFound().build());
	}
}