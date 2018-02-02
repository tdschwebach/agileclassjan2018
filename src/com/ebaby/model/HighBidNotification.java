package com.ebaby.model;

public class HighBidNotification extends Notification
{
	private final Auction auction;
	
	public HighBidNotification(Auction auction) {
		this.auction = auction;
	}
	
	@Override
	public void sendNotifications()
	{
		postOffice.sendEMail(this.auction.getSeller().getEmail(),"Your " + this.auction.getItemDescription() +
				" auction sold to bidder " + this.auction.getHighBid().getBidder().getEmail() + " for "
				+  this.auction.getFinalSellerPrice() + ".");
		postOffice.sendEMail( this.auction.getHighBid().getBidder().getEmail(),
				"Congratulations! You won an auction for " + this.auction.getItemDescription() + " from " + this.auction.getHighBid().getBidder().getEmail() +
						" for " + this.auction.getFinalBuyerPrice() + ".");
	}
}
