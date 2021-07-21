package com.example.offertest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.example.offertest.controller.UserController;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(
	webEnvironment= SpringBootTest.WebEnvironment.MOCK,
	classes = OffertestApplication.class)
@AutoConfigureMockMvc
@Slf4j
class OffertestApplicationTests {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private UserController userController;


	@Test
	void contextLoads() throws Exception {
		log.info("contextLoads Unit test");
		assertThat(userController).isNotNull();
	}

	
	@Test
	void testUserOkRegistration() throws Exception {
		
		String user = buildUserJson(1L, "Toto", "2000-08-16", "France");

		mvc.perform(post("/api/register")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(user)
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isCreated())
		           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		           .andExpect(jsonPath("$.name").value("Toto"))
		           .andExpect(jsonPath("$.birthdate").value("2000-08-16")); 
	}
	
	@Test
	void testCompleteUserOkRegistration() throws Exception {
		
		String user = buildUserJsonWithOptional(2L, "Tata", "2003-05-01", "France", 7858545L, "Female");

		mvc.perform(post("/api/register")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(user)
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isCreated())
		           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		           .andExpect(jsonPath("$.id").value(2))
		           .andExpect(jsonPath("$.name").value("Tata"))
		           .andExpect(jsonPath("$.birthdate").value("2003-05-01"))
		           .andExpect(jsonPath("$.phone").value(7858545L))
		           .andExpect(jsonPath("$.gender").value("Female")); 
	}
	
	@Test
	void testNotFrenchUser() throws Exception {
		//Spanish user not authorized, user not registered
		String user = buildUserJson(3L, "offer", "2000-08-16", "Spain");
		
		mvc.perform(post("/api/register")
		           .contentType(MediaType.APPLICATION_JSON)
		           .content(user)
		           .accept(MediaType.APPLICATION_JSON))
		           .andExpect(status().isUnauthorized())
		           .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Only adult French user can register"))); 
	}
	
	@Test
	void testNotRegisteredUser() throws Exception {
		//User 3 registration failed, so not found
		mvc.perform(get("/api/display/3")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound());
	}
	
	@Test
	void testExistingUser() throws Exception {
		//Display registered user 1 and check details
		mvc.perform(get("/api/display/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.id").value(1)) 
			      .andExpect(jsonPath("$.name").value("Toto"))
			      .andExpect(jsonPath("$.residenceCountry").value("France")); 
	}
	
	//Build Json from user informations
	private String buildUserJson(Long id, String name, String birthdate, String residence) {
		return buildUserJsonWithOptional(id, name, birthdate, residence, 0L, "");
	}
	
	private String buildUserJsonWithOptional(Long id, String name, String birthdate, String residence, long phone, String gender) {
		
		String jsonString;
		try {
			jsonString = new JSONObject()
			        .put("id", id)
			        .put("name", name)
			        .put("birthdate", birthdate)
			        .put("residenceCountry", residence)
			        .put("phone", phone)
			        .put("gender", gender)
			        .toString();
			
			return jsonString;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
