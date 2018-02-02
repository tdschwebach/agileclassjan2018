package com.ebaby.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BidTest
{
	private static final String firstName = "First";
	private static final String lastName = "Last";
	private static final String email = "email@email.org";
	private static final String userName = "user";
	private static final String password = "password1";
	private static final User buyer = new User(firstName, lastName, email, userName, password);
	private static final Integer bidPrice = 200;
	
	@Test
	void bidCanBeInstantiatedTest() {
		Bid bid = new Bid(buyer, bidPrice);
		assertAll(
				() -> assertEquals(buyer, bid.getBidder()),
				() -> assertEquals(bidPrice, bid.getBidAmount()));
	}
}
