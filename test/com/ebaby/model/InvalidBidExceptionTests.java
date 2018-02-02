package com.ebaby.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidBidExceptionTests
{
	
	private static final String errorMessage = "Error";
	
	@Test
	void retrieveUserParametersAfterConstruction() {
		InvalidBidException invalidBidException = new InvalidBidException(errorMessage);
		
		assertEquals(errorMessage, invalidBidException.getErrorMessage());
	}
	
}
