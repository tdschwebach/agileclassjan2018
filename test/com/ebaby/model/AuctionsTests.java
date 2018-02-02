package com.ebaby.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;

public class AuctionsTests
{
	private Auction auction1;
	private Auction auction2;
	private User buyer;
	private User seller;
	private static final String firstName = "First";
	private static final String lastName = "Last";
	private static final String email = "email@email.org";
	private static final String userName = "user";
	private static final String password = "password1";
	private static final String firstName2 = "First2";
	private static final String lastName2 = "Last2";
	private static final String email2 = "email2@email.org";
	private static final String userName2 = "user2";
	private static final String password2 = "password2";
	private static final String itemDescription = "ItemDesc1";
	private static final Integer startPrice = 100;
	private static final String itemDescription2 = "ItemDesc2";
	private static final Integer startPrice2 = 50;
	private static final Date startTime = new Date(System.currentTimeMillis() + 3600);
	private static final Date endTime = new Date(System.currentTimeMillis() + 7200);
	
	@BeforeEach
	void init() throws InvalidAuctionException, InterruptedException {
		buyer = new User(firstName, lastName, email, userName, password);
		seller = new User(firstName2, lastName2, email2, userName2, password2);
		seller.setLoggedIn(true);
		seller.setSeller(true);
		auction1 = new Auction(seller, itemDescription, startPrice, startTime, endTime, ItemCategory.OTHER);
		Thread.sleep(1);
		auction2 = new Auction(seller, itemDescription2, startPrice2, startTime, endTime, ItemCategory.OTHER);
	}
	
	@AfterEach
	void clearAuctionLIst(){
		Auctions auctions = Auctions.getInstance();
		auctions.getAuctionList().clear();
	}
	
	@Test
	void testAuctionListCreation() {
		Auctions auctions = Auctions.getInstance();
		assertAll(
				() -> Assertions.assertNotNull(auctions)
		);
	}
	
	@Test
	void testSingleAuctionList() {
		Auctions auctions1 = Auctions.getInstance();
		Auctions auctions2 = Auctions.getInstance();
		Assertions.assertEquals(auctions1, auctions2);
	}
	
	@Test
	void testGetAuctionbyId() {
		Auctions auctions = Auctions.getInstance();
		auctions.addAuction(auction1);
		auctions.addAuction(auction2);
		Assertions.assertEquals(auction2, auctions.getAuctionById(auction2.getAuctionId()));
	}
}
