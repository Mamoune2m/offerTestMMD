/* 
 * Class: com.example.offertest.repository.UserRepository
 * Author: Modou Mamoune DIENE
 * Date: 20/07/2021
 * Project: AF Offer Technical Test
 */

package com.example.offertest.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Document
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class User {
	
	/*To use if id auto generation needed
	 * @Transient
	public static final String SEQUENCE_NAME = "users_sequence";*/

	@Id
	private Long id;
	private String name;
	private LocalDate birthdate;
	private String residenceCountry;
	private Long phone;
	private String gender;
}
