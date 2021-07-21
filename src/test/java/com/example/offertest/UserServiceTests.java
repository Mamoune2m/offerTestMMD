package com.example.offertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.offertest.exception.UserException;
import com.example.offertest.model.User;
import com.example.offertest.repository.UserRepository;
import com.example.offertest.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Before
	public void setUp() {
		//40 years old French user
	    User user = getUser(1L, "Toto", 40, "France");
	    Mockito.when(userRepository.findById(user.getId()))
	      .thenReturn(Optional.of(user));
	    
	    Mockito.when(userRepository.save(user))
	      .thenReturn(user);
	}
	
	@Test
	public void testExistingUser() {
	    String name = "Toto";
	    User found = userService.retrieveUser(1L);
	 
	     assertThat(found.getName())
	      .isEqualTo(name);
	 }
	
	@Test
	public void testYoungUser() {
		//16 years old user not authorized (<18)
		User user = getUser(2L, "Young", 16, "France");
	    Throwable exception = assertThrows(UserException.class, () -> userService.processUserCreation(user));
	    assertEquals("Only adult user can register", exception.getMessage());
	}
	
	@Test
	public void testNoNameUser() {
		//20 years old French user with no name
		User user = getUser(2L, null, 20, "France");
	    Throwable exception = assertThrows(UserException.class, () -> userService.processUserCreation(user));
	    assertEquals("User valid name is mandatory to register", exception.getMessage());
	}
	
	@Test
	public void testNotValidIdUser() {
		//user with not valid ID
		User user = getUser(null, "NoId", 20, "France");
	    Throwable exception = assertThrows(UserException.class, () -> userService.processUserCreation(user));
	    assertEquals("Invalid user ID", exception.getMessage());
	}
	
	@Test
	public void testNotFrenchUser() {
		//No French user
		User user = getUser(2L, "NotFrench", 20, "Fran");
	    Throwable exception = assertThrows(UserException.class, () -> userService.processUserCreation(user));
	    assertEquals("Only adult French user can register", exception.getMessage());
	}
	
	private User getUser(Long id, String name, int age, String residence) {
		LocalDate birthdate = LocalDate.now().minusYears(age);
		return new User(id, name, birthdate, residence, 23L, "Male");
	}
}
