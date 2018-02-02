package com.ebaby.model;

public class SellerFeeProcessor extends CloseProcessor
{
	
	public SellerFeeProcessor(CloseProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		return true;
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		auction.setFinalSellerPrice(auction.getStartPrice() * 0.02);
	}
}
