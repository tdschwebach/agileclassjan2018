package com.ebaby.model;

public abstract class FeeProcessor
{
	private FeeProcessor tail;
	public FeeProcessor(FeeProcessor next) {
		tail = next;
	}
	
	public Auction closeAuction(Auction auction) {
		if (feeApplicable(auction)) {
			computeFee(auction);
		}
		if (tail != null) {
			return tail.closeAuction(auction);
		}
		return auction;
	}
	
	protected abstract boolean feeApplicable(Auction auction);
	protected abstract void computeFee(Auction auction);
}
