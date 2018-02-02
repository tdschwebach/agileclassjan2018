package com.ebaby.model;

public class ShippingFeeProcessor extends CloseProcessor
{
	public ShippingFeeProcessor(CloseProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.DOWNLOADABLE_SOFTWARE) {
			return false;
		} else
		{
			return true;
		}
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.CAR) {
			auction.setFinalBuyerPrice(auction.getStartPrice() + 1000.0);
		} else if (auction.getItemCategory() == ItemCategory.OTHER) {
			auction.setFinalBuyerPrice(auction.getStartPrice() + 10.0);
		}
	}

}
