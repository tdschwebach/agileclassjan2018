package com.ebaby.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Auctions
{
	private static Auctions auctions;
	private static List<Auction> auctionList;
	
	public static Auctions getInstance() {
		if (auctions == null) {
			auctionList = new ArrayList<>();
			auctions = new Auctions();
		}
		return auctions;
	}
	
	public List<Auction> getAuctionList()
	{
		return auctionList;
	}
	
	public void addAuction(Auction auction) {
		auctionList.add(auction);
	}
	
	public Auction getAuctionById(long auctionId) {
		Optional<Auction> auction =
				auctionList.stream().filter(auction1 -> auction1.getAuctionId() == auctionId).findFirst();
		if (auction.isPresent()) {
			return auction.get();
		} else {
			return null;
		}
	}
}
