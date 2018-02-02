package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;
import com.tobeagile.training.ebaby.services.PostOffice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AuctionTests
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
	private static final PostOffice postOffice = PostOffice.getInstance();
	private static final AuctionLogger auctionLogger = AuctionLogger.getInstance();
	
	@AfterEach
	void cleanUp() {
		postOffice.clear();
		auctionLogger.clearLog("./log.txt");
	}
	
	@Test
	void retrieveUAutionParametersAfterConstruction() {
		try
		{ 
			user.setLoggedIn(true);
			user.setSeller(true);
			Auction auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			
			assertAll(
					() -> assertEquals(user, auction.getSeller()),
					() -> assertEquals(itemDescription, auction.getItemDescription()),
					() -> assertEquals(startPrice, auction.getStartPrice()),
					() -> assertEquals(startTime, auction.getStartTime()),
					() -> assertEquals(endTime, auction.getEndTime()),
					() -> assertEquals(AuctionStatus.SCHEDULED, auction.getAuctionStatus()),
					() -> assertTrue(auction.getAuctionId() > 0)
			);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not exptected");
		}

	}
	
	@Test
	void startAuctionTests(){
		user.setLoggedIn(true);
		user.setSeller(true);
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		assertEquals(AuctionStatus.STARTED, auction.getAuctionStatus());
	}
	
	@Test
	void validUserCanStartAuction() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not exptected");
		}
	}
	
	@Test
	void userThatIsntSellerCanNotStartAuction() {
		user.setSeller(false);
		user.setLoggedIn(true);

		
		InvalidAuctionException exception = assertThrows(InvalidAuctionException.class, () -> {
			new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		});
		assertEquals("User is not a seller. Can not create auction.", exception.getErrorMessage());
		
		
	}
	
	@Test
	void userThatIsntLoggedInCanNotStartAuction() {
		user.setSeller(true);
		user.setLoggedIn(false);
		
		
		InvalidAuctionException exception = assertThrows(InvalidAuctionException.class, () -> {
			new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		});
		assertEquals("User is not logged in. Can not create auction.", exception.getErrorMessage());
		
		
	}
	
	@Test
	void creationFailsWithInvalidStartTime() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		InvalidAuctionException exception = assertThrows(InvalidAuctionException.class, () -> {
			new Auction(user, itemDescription, startPrice,  new Date(System.currentTimeMillis() - 3600 * 1000), endTime, ItemCategory.OTHER);
		});
		assertEquals("Start time is in the past. Can not create auction.", exception.getErrorMessage());
		
		
	}
	
	@Test
	void creationFailsWithInvalidEndTime() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		InvalidAuctionException exception = assertThrows(InvalidAuctionException.class, () -> {
			new Auction(user, itemDescription, startPrice,  startTime, new Date(System.currentTimeMillis() - 3600 * 1000), ItemCategory.OTHER);
		});
		assertEquals("End time is before start time. Can not create auction.", exception.getErrorMessage());
		
		
	}
	
	@Test
	void ValidBidOnStartedAuction() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid validBid =new Bid(bidder, 200);
		
		try
		{
			auction.placeBid(validBid);
		}
		catch (InvalidBidException iae) {
			fail("Invalid Bid Exception was not expected");
		}
		
		assertNotNull(auction.getHighBid());
		assertEquals(auction.getHighBid().getBidder().getUserName(), validBid.getBidder().getUserName());
		assertEquals(auction.getHighBid().getBidAmount(), validBid.getBidAmount());
	}
	
	@Test
	void userCannotBidOnTheirOwnItemInStartedAuction() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid bid =new Bid(user, 200);
		
		try
		{
			auction.placeBid(bid);
		}
		catch (InvalidBidException ibe) {
			assertEquals("Bidder cannot bid on its own item.", ibe.getErrorMessage());
		}
	}
	
	@Test
	void userCannotBidWhenItemPriceIsLowerThanStartPriceInStartedAuction() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		Bid bid =new Bid(bidder, 50);
		
		try
		{
			auction.placeBid(bid);
		}
		catch (InvalidBidException ibe) {
			assertEquals("Bidder cannot bid lower than start price.", ibe.getErrorMessage());
		}
	}
	
	@Test
	void userCannotBidWhenItemPriceIsLowerThanHighBidPriceInStartedAuction() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		Bid highBid = new Bid(bidder, 500);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.setHighBid(highBid);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid bid = new Bid(bidder, 400);
		
		try
		{
			auction.placeBid(bid);
		}
		catch (InvalidBidException ibe) {
			assertEquals("Bidder cannot bid <= than the highest bid.", ibe.getErrorMessage());
		}
	}
	
	@Test
	void userCannotBidWhenNotLoggedIn() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid bid = new Bid(bidder, 400);
		
		try
		{
			auction.placeBid(bid);
		}
		catch (InvalidBidException ibe) {
			assertEquals("Bidder must be logged in to bid.", ibe.getErrorMessage());
		}
	}
	
	@Test
	void testCloseStartedAuction() {
		user.setLoggedIn(true);
		user.setSeller(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.setAuctionStatus(AuctionStatus.STARTED);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.close();
		assertEquals(AuctionStatus.FINISHED, auction.getAuctionStatus());
	}
	
	@Test
	void testCloseScheduledAuction() {
		user.setLoggedIn(true);
		user.setSeller(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			assertEquals(AuctionStatus.SCHEDULED, auction.getAuctionStatus());
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.close();
		assertEquals(AuctionStatus.SCHEDULED, auction.getAuctionStatus());
	}
	
	@Test
	void testStartScheduledAuction() {
		user.setLoggedIn(true);
		user.setSeller(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			assertEquals(AuctionStatus.SCHEDULED, auction.getAuctionStatus());
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.start();
		assertEquals(AuctionStatus.STARTED, auction.getAuctionStatus());
	}
	
	
	@Test
	void testFinishCannotStart() {
		user.setLoggedIn(true);
		user.setSeller(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.start();
			auction.close();
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.start();
		assertEquals(AuctionStatus.FINISHED, auction.getAuctionStatus());
	}
	
	
	@Test
	void testCloseStartedAuctionWithNoBiddersSendsEmail() {
		user.setLoggedIn(true);
		user.setSeller(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
			auction.start();
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.close();
		assertEquals(AuctionStatus.FINISHED, auction.getAuctionStatus());
		
		assertTrue(postOffice.doesLogContain(user.getEmail(), "Sorry, your auction for " + itemDescription +
			" did not have any bidders."));
	}
	
	@Test
	void ValidBidOnStartedAuctionSendsEmailToSellerWithItemCategoryOTHER() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, 15000, startTime, endTime, ItemCategory.OTHER);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid validBid =new Bid(bidder, 15200);
		
		try
		{
			auction.placeBid(validBid);
		}
		catch (InvalidBidException iae) {
			fail("Invalid Bid Exception was not expected");
		}
		
		auction.close();

		assertNotNull(auction.getHighBid());
		assertEquals(auction.getHighBid().getBidder().getUserName(), validBid.getBidder().getUserName());
		assertEquals(auction.getHighBid().getBidAmount(), validBid.getBidAmount());
		
		assertTrue(postOffice.doesLogContain(bidder.getEmail(), "Congratulations! You won an auction for " 
				+ itemDescription + " from " + user.getEmail() + " for " +  auction.getFinalBuyerPrice() + "."));
		assertTrue(postOffice.doesLogContain(user.getEmail(), "Your " + itemDescription +
				" auction sold to bidder " + auction.getHighBid().getBidder().getEmail() + " for "
				+  auction.getFinalSellerPrice() + "."));
		//assertTrue(auctionLogger.findMessage("./log.txt", "An item was sold for " + buyerAmount));
		
	}
	
	@Test
	void ValidBidOnStartedAuctionSendsEmailToSellerWithItemCategoryDOWNLOADABLE() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.DOWNLOADABLE_SOFTWARE);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid validBid =new Bid(bidder, 200);
		
		try
		{
			auction.placeBid(validBid);
		}
		catch (InvalidBidException iae) {
			fail("Invalid Bid Exception was not expected");
		}
		
		auction.close();

		assertNotNull(auction.getHighBid());
		assertEquals(auction.getHighBid().getBidder().getUserName(), validBid.getBidder().getUserName());
		assertEquals(auction.getHighBid().getBidAmount(), validBid.getBidAmount());
		
		/*assertTrue(postOffice.doesLogContain(bidder.getEmail(), "Congratulations! You won an auction for "
				+ itemDescription + " from " + user.getEmail() + " for " +  buyerAmount + "."));
		assertTrue(postOffice.doesLogContain(user.getEmail(), "Your " + itemDescription +
				" auction sold to bidder " + auction.getHighBid().getBidder().getEmail() + " for "
				+  sellerAmount + "."));*/
	}
	
	@Test
	void ValidBidOnStartedAuctionSendsEmailToSellerWithItemCategoryCAR() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.CAR);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid validBid =new Bid(bidder, 200);
		
		try
		{
			auction.placeBid(validBid);
		}
		catch (InvalidBidException iae) {
			fail("Invalid Bid Exception was not expected");
		}
		
		auction.close();

		assertNotNull(auction.getHighBid());
		assertEquals(auction.getHighBid().getBidder().getUserName(), validBid.getBidder().getUserName());
		assertEquals(auction.getHighBid().getBidAmount(), validBid.getBidAmount());
		
		assertTrue(postOffice.doesLogContain(bidder.getEmail(), "Congratulations! You won an auction for "
				+ itemDescription + " from " + user.getEmail() + " for " +  auction.getFinalBuyerPrice() + "."));
		assertTrue(postOffice.doesLogContain(user.getEmail(), "Your " + itemDescription +
				" auction sold to bidder " + auction.getHighBid().getBidder().getEmail() + " for "
				+  auction.getFinalSellerPrice() + "."));
		//assertTrue(auctionLogger.findMessage("./log.txt", "A car was sold for " + buyerAmount));
	}
	
	@Test
	void ValidBidOnStartedAuctionSendsEmailToSellerWithItemCategoryLuxuryCAR() {
		user.setSeller(true);
		user.setLoggedIn(true);
		
		User bidder = new User(firstName, lastName, email, "new user", password);
		bidder.setLoggedIn(true);
		
		Auction auction = null;
		try
		{
			auction = new Auction(user, itemDescription, startPrice, startTime, endTime, ItemCategory.CAR);
		}
		catch (InvalidAuctionException iae) {
			fail("Invalid Auction Exception was not expected");
		}
		auction.setAuctionStatus(AuctionStatus.STARTED);
		
		Bid validBid =new Bid(bidder, 50001);
		
		try
		{
			auction.placeBid(validBid);
		}
		catch (InvalidBidException iae) {
			fail("Invalid Bid Exception was not expected");
		}
		
		auction.close();

		assertNotNull(auction.getHighBid());
		assertEquals(auction.getHighBid().getBidder().getUserName(), validBid.getBidder().getUserName());
		assertEquals(auction.getHighBid().getBidAmount(), validBid.getBidAmount());
		
		/*assertTrue(postOffice.doesLogContain(bidder.getEmail(), "Congratulations! You won an auction for "
				+ itemDescription + " from " + user.getEmail() + " for " +  buyerAmount + "."));
		assertTrue(postOffice.doesLogContain(user.getEmail(), "Your " + itemDescription +
				" auction sold to bidder " + auction.getHighBid().getBidder().getEmail() + " for "
				+  sellerAmount + "."));*/
	}
	
}
