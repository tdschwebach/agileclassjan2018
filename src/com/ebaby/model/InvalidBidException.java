package com.ebaby.model;

public class InvalidBidException extends Throwable
{
	private String errorMessage;
	
	public InvalidBidException(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
}
