/* 
 * Class: com.example.offertest.exception.UserException
 * Author: Modou mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.exception;

public class UserException extends RuntimeException {
	
	public UserException(Long id) {
        super(String.format("User with id %d does not exist",id));
    }
    
    public UserException(String errorText) {
        super(errorText);
    }
}
