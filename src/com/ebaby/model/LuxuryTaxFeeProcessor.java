package com.ebaby.model;

public class LuxuryTaxFeeProcessor extends CloseProcessor
{
	
	public LuxuryTaxFeeProcessor(CloseProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.CAR && auction.getHighBid().getBidAmount() > 50000) {
			return true;
		} else
		{
			return false;
		}
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		auction.setFinalBuyerPrice(auction.getHighBid().getBidAmount() * 0.04);
	}
}
