package com.ebaby.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SystemUsers
{
	private static SystemUsers systemUsers;
	private static List<User> userList;
	
	public static SystemUsers getInstance() {
		if (systemUsers == null) {
			userList = new ArrayList<>();
			systemUsers = new SystemUsers();
		}
		return systemUsers;
	}
	
	public List<User> getSystemUsers()
	{
		return userList;
	}
	
	public void registerUser(User user) {
		if (!userList.stream().anyMatch(user1 -> user1.getUserName().equalsIgnoreCase(user.getUserName())))
		userList.add(user);
	}
	
	public boolean registerAsSeller(String userName) {
		User user = getByUserName(userName);
		if (user != null) {
			user.setSeller(true);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean logIn(String userName, String password)
	{
		User user = getByUserName(userName);
		if (user != null) {
			user.setLoggedIn(user.getPassword().equals(password));
			return user.isLoggedIn();
		} else {
			return false;
		}
	}
	
	public boolean logOut(String userName) {
		User user = getByUserName(userName);
		if (user != null) {
			user.setLoggedIn(false);
			return true;
		} else {
			return false;
		}
	}
	
	public User getByUserName(String userName) {
		Optional<User> user =
				userList.stream().filter(user1 -> user1.getUserName().equalsIgnoreCase(userName)).findFirst();
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
}
