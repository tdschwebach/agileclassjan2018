package com.ebaby.model;

public class DownloadableSoftwareCalculator extends PriceCalculator
{
	private Auction auction;
	
	public DownloadableSoftwareCalculator(Auction auction)
	{
		this.auction = auction;
	}
	
	@Override
	public Double calculateBuyerTotal()
	{
		Double buyerAmount = this.auction.getHighBid().getBidAmount().doubleValue();
		return buyerAmount;
	}
}
