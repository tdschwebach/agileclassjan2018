package com.ebaby.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests
{
	private static final String firstName = "First";
	private static final String lastName = "Last";
	private static final String email = "email@email.org";
	private static final String userName = "user";
	private static final String password = "password1";
	
	@Test
	void retrieveUserParametersAfterConstruction() {
		User user = new User(firstName, lastName,  email, userName, password);
		
		assertAll(
				() -> assertEquals(firstName, user.getFirstName()),
				() -> assertEquals(lastName, user.getLastName()),
				() -> assertEquals(email, user.getEmail()),
				() -> assertEquals(userName, user.getUserName()),
				() -> assertEquals(password, user.getPassword()),
				() -> assertEquals(false, user.isLoggedIn())
		);
	}
	
	@Test 
	void userLoginTest(){
		User user = new User(firstName, lastName,  email, userName, password);
		user.setLoggedIn(true);
		assertTrue(user.isLoggedIn());
	}
	
	@Test
	void createSeller() {
		User user = new User(firstName, lastName,  email, userName, password);
		user.setSeller(true);
		assertTrue(user.isSeller());
	}
}
