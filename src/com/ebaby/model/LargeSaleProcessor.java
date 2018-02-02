package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;

import static com.ebaby.model.Constants.HIGH_BID_AMOUNT;

public class LargeSaleProcessor extends CloseProcessor
{
	private final AuctionLogger auctionLogger;
	
	public LargeSaleProcessor(CloseProcessor next)
	{
		super(next);
		auctionLogger = AuctionLogger.getInstance();
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		return auction.getHighBid() != null && auction.getHighBid().getBidAmount() > HIGH_BID_AMOUNT;
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		this.auctionLogger.log(Constants.LARGE_SALE_LOG, "An item was sold for " + auction.getHighBid().getBidAmount());
	}
}
