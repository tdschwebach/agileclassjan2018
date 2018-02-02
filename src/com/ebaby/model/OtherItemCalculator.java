package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class OtherItemCalculator extends PriceCalculator
{
	private Auction auction;
	private AuctionLogger auctionLogger;
	
	
	public OtherItemCalculator(Auction auction)
	{
		this.auction = auction;
		this.auctionLogger = AuctionLogger.getInstance();
	}
	
	@Override
	public Double calculateBuyerTotal()
	{
		Double buyerAmount = this.auction.getHighBid().getBidAmount().doubleValue() + 10;
		if (buyerAmount > 10000.0)
		{
			auctionLogger.log("./log.txt", "An item was sold for " + buyerAmount);
		}
		return buyerAmount;
	}
}

