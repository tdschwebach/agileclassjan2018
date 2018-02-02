package com.ebaby.model;

public abstract class PriceCalculator
{
	public static PriceCalculator getInstance(Auction auction) {
		if (auction.getItemCategory() == ItemCategory.CAR) {
			return new CarCalculator(auction);
		} else if (auction.getItemCategory() == ItemCategory.DOWNLOADABLE_SOFTWARE) {
			return new DownloadableSoftwareCalculator(auction);
		} else {
			return new OtherItemCalculator(auction);
		}
	}
	
	public Double calculateSellerTotal(Integer bidAmount) {
		return bidAmount - bidAmount * 0.02;
		
	}
	public abstract Double calculateBuyerTotal();
	
}
