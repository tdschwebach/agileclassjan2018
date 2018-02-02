package com.ebaby.model;

public class LuxuryTaxFeeProcessor extends FeeProcessor
{
	
	public LuxuryTaxFeeProcessor(FeeProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean feeApplicable(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.CAR && auction.getHighBid().getBidAmount() > 50000) {
			return true;
		} else
		{
			return false;
		}
	}
	
	@Override protected void computeFee(Auction auction)
	{
		auction.setFinalBuyerPrice(auction.getHighBid().getBidAmount() * 0.04);
	}
}
