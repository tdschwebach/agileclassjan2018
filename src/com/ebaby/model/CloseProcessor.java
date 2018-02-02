package com.ebaby.model;

public abstract class CloseProcessor
{
	private CloseProcessor tail;
	public CloseProcessor(CloseProcessor next) {
		tail = next;
	}
	
	public Auction closeAuction(Auction auction) {
		if (processorApplicable(auction)) {
			applyProcessor(auction);
		}
		if (tail != null) {
			return tail.closeAuction(auction);
		}
		return auction;
	}
	
	protected abstract boolean processorApplicable(Auction auction);
	protected abstract void applyProcessor(Auction auction);
}
