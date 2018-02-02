package com.ebaby.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidAuctionExceptionTests
{
	
	private static final String errorMessage = "Error";
	
	@Test
	void retrieveUserParametersAfterConstruction() {
		InvalidAuctionException invalidAuctionException = new InvalidAuctionException(errorMessage);
		
		assertEquals(errorMessage, invalidAuctionException.getErrorMessage());
	}
	
}
