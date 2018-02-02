package com.ebaby.model;

public class SellerFeeProcessor extends FeeProcessor
{
	
	public SellerFeeProcessor(FeeProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean feeApplicable(Auction auction)
	{
		return true;
	}
	
	@Override protected void computeFee(Auction auction)
	{
		auction.setFinalSellerPrice(auction.getStartPrice() * 0.02);
	}
}
