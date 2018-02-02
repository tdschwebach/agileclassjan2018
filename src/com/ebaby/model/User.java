package com.ebaby.model;

public class User
{
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private Boolean isLoggedIn;
	private Boolean seller;
	
	public User(String firstName, String lastName, String email, String userName, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.isLoggedIn = false;
		this.seller = false;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Boolean isLoggedIn()
	{
		return isLoggedIn;
	}
	
	public void setLoggedIn(Boolean loggedIn)
	{
		isLoggedIn = loggedIn;
	}
	
	public Boolean isSeller()
	{
		return seller;
	}
	
	public void setSeller(Boolean seller)
	{
		this.seller = seller;
	}
}
