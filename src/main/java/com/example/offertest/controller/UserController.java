/* 
 * Class: com.example.offertest.controller.UserController
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.offertest.exception.UserException;
import com.example.offertest.model.User;
import com.example.offertest.service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException{
    	try {
    		//Add user to database
	    	User savedUser = userService.processUserCreation(user);
	    	return new ResponseEntity<>(savedUser, HttpStatus. CREATED);
    	}
    	catch (UserException e) {
    		//User inputs data not valid 
    		throw new ResponseStatusException(
    		           HttpStatus.UNAUTHORIZED, e.getMessage());
    	}
    }
    
    @GetMapping("/display/{id}")
    public ResponseEntity<User> displayUser(@PathVariable Long id) throws UserException{
    	try {
    		//Retrieve user from database
        	User foundUser = userService.retrieveUser(id);
        	return new ResponseEntity<>(foundUser, HttpStatus.OK);
    	}
    	catch (UserException e) {
    		//User not present in DB
    		throw new ResponseStatusException(
    		           HttpStatus.NOT_FOUND, e.getMessage(), e);
    	}
    }
}
