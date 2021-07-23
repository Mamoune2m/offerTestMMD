/* 
 * Class: com.example.offertest.service.UserService
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.service;


import com.example.offertest.model.User;

public interface UserService {

	public User processUserCreation(User user);
	public User retrieveUser(Long userId);
	public User retrieveUserByName(String name);
	
}
