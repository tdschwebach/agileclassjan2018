package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class OffHoursProcessorTest
{
	private static final String firstName = "First";
	private static final String lastName = "Last";
	private static final String email = "email@email.org";
	private static final String userName = "user";
	private static final String password = "password1";
	private static final User user = new User(firstName, lastName, email, userName, password);
	private static final String itemDescription = "ItemDesc1";
	private static final Integer startPrice = 100;
	private static final Date startTime = new Date(System.currentTimeMillis() + 3600);
	private static final Date endTime = new Date(System.currentTimeMillis() + 7200);
	private static final AuctionLogger auctionLogger = AuctionLogger.getInstance();
	
	@AfterEach
	void clear() {
		auctionLogger.clearLog(Constants.OFF_HOURS_LOG);
	}
	@Test
	void testOffHoursLogging() {
		Auction auction = null;
		user.setLoggedIn(true);
		user.setSeller(true);
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.onStart();
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected: " +  iae.getErrorMessage());
		}
		auction.onClose();
		OffHoursProcessor processor = new OffHoursProcessor(null);
		processor.setAsTest();
		processor.setOffHours(true);
		if (processor.processorApplicable(auction))
		{
			processor.applyProcessor(auction);
		}
		assertTrue(auctionLogger.findMessage(Constants.OFF_HOURS_LOG, "Logging Sale for Off Hours" + auction.getAuctionId()));
	}
	
	@Test
	void testNotOffHoursLogging() {
		Auction auction = null;
		user.setLoggedIn(true);
		user.setSeller(true);
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.onStart();
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected: " +  iae.getErrorMessage());
		}
		OffHoursProcessor processor = new OffHoursProcessor(null);
		processor.setAsTest();
		processor.setOffHours(false);
		if (processor.processorApplicable(auction))
		{
			processor.applyProcessor(auction);
		}
		assertFalse(auctionLogger.findMessage(Constants.OFF_HOURS_LOG, "Logging Sale for Off Hours" + auction.getAuctionId()));
	}
}
