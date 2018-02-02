package com.ebaby.model;

public class InvalidAuctionException extends Throwable
{
	private String errorMessage;
	
	public InvalidAuctionException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
}
