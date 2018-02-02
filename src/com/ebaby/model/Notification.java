package com.ebaby.model;

import com.tobeagile.training.ebaby.services.PostOffice;

public abstract class Notification
{
	protected final PostOffice postOffice = PostOffice.getInstance();
	
	public static Notification getInstance(Auction auction) {
		if (auction.getHighBid() == null) {
			return new NoBidNotification(auction);
		} else {
			return new HighBidNotification(auction);
		}
	}
	
	public abstract void sendNotifications();
}
