package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;

public class CarLogProcessor extends CloseProcessor
{
	private final AuctionLogger auctionLogger;
	
	public CarLogProcessor(CloseProcessor next)
	{
		super(next);
		auctionLogger = AuctionLogger.getInstance();
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.CAR) {
			return true;
		} else
		{
			return false;
		}
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		this.auctionLogger.log(Constants.CAR_LOG_LOG, "A car was sold for " + auction.getHighBid().getBidAmount());
	}
}

