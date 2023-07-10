package com.canterosolutions.restapiexample.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.canterosolutions.restapiexample.jpa.UserRepository;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserJpaController {
	
	//@Autowired
    //private UserDAOService service;
	
	@Autowired
	private UserRepository repository;
	
	// Easy Version 
	@GetMapping(path="/jpa/users")
	public List<User> retrieveAllUsers() {
		return repository.findAll();
	}
	
	@GetMapping(path="/jpa/v2/users")
	public ResponseEntity<CollectionModel<User>> retrieveAllUsersWithLink() {
		List<User> users = repository.findAll();
		users.forEach(user -> {
			WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveUserID(user.getId()));
			user.add(linkTo.withSelfRel());
		});
		
		return ResponseEntity.ok(CollectionModel.of(users));
	}
	
	@GetMapping(path="/jpa/users/{user_id}")
	public ResponseEntity<Optional<User>> retrieveUserID(@PathVariable Integer user_id) {
		
		Optional<User> user = repository.findById(user_id);
		if(user == null || user.isEmpty())
			throw new UserNotFoundException("User Not Found with id:"+String.valueOf(user_id));
		
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUserID(@PathVariable Integer id) {
		repository.deleteById(id);
	}
	
	@PostMapping(path="/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").
				buildAndExpand(savedUser.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}

}
