package com.ebaby.model;

public class NoBidNotification extends Notification
{
	private final Auction auction;
	
	public NoBidNotification(Auction auction) {
		this.auction = auction;
	}
	
	@Override
	public void sendNotifications()
	{
		postOffice.sendEMail(this.auction.getSeller().getEmail(),
				"Sorry, your auction for " + this.auction.getItemDescription() +
					" did not have any bidders.");
	}
}
