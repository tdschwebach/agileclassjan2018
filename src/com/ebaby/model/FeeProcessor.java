package com.ebaby.model;

public abstract class FeeProcessor
{
	private FeeProcessor tail;
	public FeeProcessor(FeeProcessor next) {
		tail = next;
	}
	
	public Auction closeAuction(Auction auction) {
		return auction;
	}
	
	protected abstract Double computeFee(Integer bidAmount);
	//protected abstract Double addShippingFee(Integer bidAmount);
	//protected abstract Double addLuxuryTax(Integer bidAmount);
	//protected abstract Double addCarShippingFee(Integer bidAmount);
}
