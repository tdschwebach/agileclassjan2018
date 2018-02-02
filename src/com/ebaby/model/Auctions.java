package com.ebaby.model;

import com.tobeagile.training.ebaby.services.Auctionable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Auctions implements Auctionable
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
	
	@Override public void handleAuctionEvents(long now)
	{
		for (Auction auction : auctionList) {
			if (auction.getAuctionStatus() == AuctionStatus.SCHEDULED &&
					auction.getStartTime().after(new Date(now))) {
				auction.onStart();
			} else if (auction.getAuctionStatus() == AuctionStatus.STARTED &&
					auction.getEndTime().before(new Date(now))) {
				auction.onClose();
			}
		}
	}
}
