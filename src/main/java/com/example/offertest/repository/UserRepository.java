/* 
 * Class: com.example.offertest.repository.UserRepository
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.offertest.model.User;

public interface UserRepository extends MongoRepository<User, Long> {

}
