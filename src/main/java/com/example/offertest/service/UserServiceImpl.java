/* 
 * Class: com.example.offertest.service.UserServiceImpl
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.service;

import java.time.Period;
import java.util.Collection;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.offertest.exception.UserException;
import com.example.offertest.model.User;
import com.example.offertest.repository.UserRepository;


@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

	@Override
	public User processUserCreation(User user) {
		
		//User validation
		validateUser(user);

		//Register user as all mandatory info correct
		User savedUser = userRepository.save(user);
		return savedUser;
	}



	@Override
	public User retrieveUser(Long userId) {
		User foundUser = userRepository.findById(userId).orElseThrow(() -> new UserException(userId));
		return foundUser;
	}

	@Override
	public User retrieveUserByName(String name) {
		Collection<User> foundUsers = userRepository.findByName(name);
		if(foundUsers.isEmpty()) {
			throw new UserException("No User with name "+ name + " found");
		} else if (foundUsers.size() > 1) {
			throw new UserException("Several users with name "+ name + " exist, please use id");
		}
		
		return foundUsers.stream().findFirst().get();
	}
	
	//User validations methods
	private void validateUser(User user) {

		//Validate ID and check if already registered
		validateUserId(user);

		//Block non adult user registration
		validateUserAge(user.getBirthdate());
		
		//Only French users are authorized
		validateResidence(user.getResidenceCountry());
	
		//Verify name input
		validateUserName(user.getName());
	}

	//Validate ID and check if already stored
	private void validateUserId(User user) throws UserException{
		if(user.getId() == null) {
			throw new UserException("Invalid user ID");
		}
		
		userRepository.findById(user.getId()).ifPresent(usr -> {
            throw new UserException("User with id "+usr.getId() + " already exists");
        });
	}
	
	//Validate country of residence
	private void validateResidence(String residence) throws UserException{
		//Only French users are authorized
		if(!isFrenchResident(residence)) {
			throw new UserException("Only adult French user can register");
		}
	}
	//Check if user residence is France
	public boolean isFrenchResident(String residence) {
		return ((residence != null) && residence.toUpperCase().equals("FRANCE"));
	}
	
	//Validate user name
	private void validateUserName(String userName) throws UserException{
		if(userName == null || userName.isEmpty()) {
			throw new UserException("User valid name is mandatory to register");
		}
	}

	//Validate user age
	private void validateUserAge(LocalDate birthdate) throws UserException{
		if(!isAdultUser(birthdate)) {
			throw new UserException("Only adult user can register");
		}
	}

	//Check if user is Adult (age > 18 years
	public boolean isAdultUser(LocalDate birthdate) {
		boolean isAdult = false;
		if(birthdate!= null) {
			int age = Period.between(birthdate, LocalDate.now()).getYears();
			isAdult = age >= 18;
		}

		return isAdult;
	}

}
