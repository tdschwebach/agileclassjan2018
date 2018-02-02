package com.ebaby.model;

public class ShippingFeeProcessor extends FeeProcessor
{
	public ShippingFeeProcessor(FeeProcessor next)
	{
		super(next);
	}
	
	@Override protected boolean feeApplicable(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.DOWNLOADABLE_SOFTWARE) {
			return false;
		} else
		{
			return true;
		}
	}
	
	@Override protected void computeFee(Auction auction)
	{
		if (auction.getItemCategory() == ItemCategory.CAR) {
			auction.setFinalBuyerPrice(auction.getStartPrice() + 1000);
		} else if (auction.getItemCategory() == ItemCategory.OTHER) {
			auction.setFinalBuyerPrice(auction.getStartPrice() + 10);
		}
	}

}
