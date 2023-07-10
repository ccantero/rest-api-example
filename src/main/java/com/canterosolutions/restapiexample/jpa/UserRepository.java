package com.canterosolutions.restapiexample.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canterosolutions.restapiexample.user.User;


public interface UserRepository extends JpaRepository<User, Integer> {

}
