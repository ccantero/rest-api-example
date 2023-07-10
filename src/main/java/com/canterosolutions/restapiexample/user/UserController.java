package com.canterosolutions.restapiexample.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserController {
	
	@Autowired
    private UserDAOService service;
	
	/*
	 * OLD one - Before HATEOAS
	 */
	/*
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	*/
	
	@GetMapping(path="/users")
	public ResponseEntity<CollectionModel<User>> retrieveAllUsers() {
		List<User> users = service.findAll();
		users.forEach(user -> {
			WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveUserID(user.getId()));
			user.add(linkTo.withSelfRel());
		});
		
		return ResponseEntity.ok(CollectionModel.of(users));
	}
	
	//@GetMapping(path="/users/{user_id}")
	public User retrieveUserID(@PathVariable String user_id) {
		return service.findUser(user_id);
	}
	
	/*
	 * OLD one - Before HATEOAS
	 */
	/*
	@GetMapping(path="/users/{id}")
	public User retrieveUserID(@PathVariable Integer id) {
		User user = service.findUser(id);
		if(user == null)
			throw new UserNotFoundException("id:"+id);
		return user;
	}
	*/
	
	@GetMapping(path="/users/{id}")
	public EntityModel<User> retrieveUserID(@PathVariable Integer id) {
		User user = service.findUser(id);
		if(user == null)
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<User> entity = EntityModel.of(user); 
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entity.add(link.withRel("all-users"));
		
		return entity;
	}
	
	@PostMapping(path="/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").
				buildAndExpand(savedUser.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUserID(@PathVariable Integer id) {
		service.deleteUser(id);
	}
}
