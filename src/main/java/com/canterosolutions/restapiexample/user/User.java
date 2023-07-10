package com.canterosolutions.restapiexample.user;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name = "user_details")
public class User extends RepresentationModel<User>  {

	@Id
	@GeneratedValue
	private Integer id;
	@Size(min=2, message = "Name should have at least 2 characters")
	@JsonProperty("user_name")
	private String name;
	@Past(message = "birthDate cannot be in the future.")
	private LocalDate birthDate;
	

	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	
	// Default constructor is necessary for JPA Integration
	protected User() {
	}

	
	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
