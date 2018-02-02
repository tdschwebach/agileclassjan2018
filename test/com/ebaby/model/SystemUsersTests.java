package com.ebaby.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemUsersTests
{
	private final String firstName = "First";
	private final String lastName = "Last";
	private final String email = "email@email.org";
	private final String userName = "user";
	private final String password = "password1";
	private final String firstName2 = "First2";
	private final String lastName2 = "Last2";
	private final String email2 = "email2@email.org";
	private final String userName2 = "user2";
	private final String password2 = "password12";
	private User user1;
	private User user2;
	
	@BeforeEach
	void init() {
		user1 = new User(firstName, lastName, email, userName, password);
		user2 = new User(firstName2, lastName2, email2, userName2, password2);
	}
	
	@AfterEach
	void clearUsersList(){
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.getSystemUsers().clear();
	}
	
	@Test
	void testSystemUsersCreation() {
		SystemUsers systemUsers = SystemUsers.getInstance();
		assertAll(
				() -> Assertions.assertNotNull(systemUsers)
		);
	}
	
	@Test
	void testSingleSystemUserList() {
		SystemUsers userList1 = SystemUsers.getInstance();
		SystemUsers userList2 = SystemUsers.getInstance();
		Assertions.assertEquals(userList1, userList2);
	}
	
	@Test
	void testRegisterUser() {
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		assertAll(
				() -> Assertions.assertNotNull(systemUsers),
				() -> Assertions.assertEquals(1, systemUsers.getSystemUsers().size())
		);
	}
	
	@Test
	void duplicateUser() {
		User user1 = new User(firstName, lastName, email, userName, password);
		User user2 = new User(firstName, lastName, email, userName, password);
		
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user1);
		systemUsers.registerUser(user2);
		Assertions.assertEquals(1, systemUsers.getSystemUsers().size());
	}
	
	@Test
	void loginUserWithValidCredentials(){
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean loggedIn = systemUsers.logIn(user.getUserName(), user.getPassword());
		
		assertTrue(loggedIn);
	}
	
	@Test
	void loginUserWithInvalidUsername(){
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean loggedIn = systemUsers.logIn("baduser", user.getPassword());
		
		assertFalse(loggedIn);
	}
	
	@Test
	void loginUserWithInvalidPassword(){
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean loggedIn = systemUsers.logIn(user.getUserName(), "badpassword");
		
		assertFalse(loggedIn);
	}
	
	@Test
	void testUserLogout() {
		User user = new User(firstName, lastName, email, userName, password);
		user.setLoggedIn(true);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean loggedOut = systemUsers.logOut(userName);
		
		assertTrue(loggedOut);
		assertFalse(user.isLoggedIn());
	}
	
	@Test
	void testUserLogoutWithInvalidUserName() {
		User user = new User(firstName, lastName, email, userName, password);
		user.setLoggedIn(true);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean loggedOut = systemUsers.logOut("bad user");
		
		assertFalse(loggedOut);
	}
	
	@Test
	void testRegisterSellerValidUsername() {
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean registeredSeller = systemUsers.registerAsSeller(userName);
		
		assertTrue(registeredSeller);
		assertTrue(user.isSeller());
	}
	
	@Test
	void testRegisterSellerInvalidUsername() {
		User user = new User(firstName, lastName, email, userName, password);
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user);
		
		boolean registeredSeller = systemUsers.registerAsSeller("bad user");
		
		assertFalse(registeredSeller);
	}
	
	@Test
	void testGetSystemUserByUsername() {
		SystemUsers systemUsers = SystemUsers.getInstance();
		systemUsers.registerUser(user1);
		systemUsers.registerUser(user2);
		
		User retrievedUser = systemUsers.getByUserName(userName2);
		
		assertEquals(user2, retrievedUser);

	}
}
