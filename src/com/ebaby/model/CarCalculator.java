package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class CarCalculator extends PriceCalculator
{
	private Auction auction;
	private AuctionLogger auctionLogger;
	
	public CarCalculator(Auction auction)
	{
		this.auction = auction;
		this.auctionLogger = AuctionLogger.getInstance();
	}
	
	@Override public Double calculateBuyerTotal()
	{
		Double buyerAmount = this.auction.getHighBid().getBidAmount().doubleValue();
		if (auction.getHighBid().getBidAmount() > 50000) {
			buyerAmount = buyerAmount + buyerAmount * .04;
		}
		buyerAmount += 1000;
		auctionLogger.log("./log.txt", "A car was sold for " + buyerAmount);
		return buyerAmount;
	}
}
