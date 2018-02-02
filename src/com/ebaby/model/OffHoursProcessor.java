package com.ebaby.model;

import com.tobeagile.training.ebaby.services.AuctionLogger;
import com.tobeagile.training.ebaby.services.Hours;
import com.tobeagile.training.ebaby.services.OffHours;
import com.tobeagile.training.ebaby.services.OffHoursMock;

public class OffHoursProcessor extends CloseProcessor
{
	private final AuctionLogger auctionLogger;
	private Hours offHours;
	
	public OffHoursProcessor(CloseProcessor next)
	{
		super(next);
		auctionLogger = AuctionLogger.getInstance();
		offHours = OffHours.getInstance();
	}
	
	public void setAsTest() {
		offHours = OffHoursMock.getInstance();
	}
	
	public void setOffHours(boolean value) {
		offHours.setOffHours(value);
	}
	
	@Override protected boolean processorApplicable(Auction auction)
	{
		return offHours.isOffHours();
	}
	
	@Override protected void applyProcessor(Auction auction)
	{
		this.auctionLogger.log(Constants.OFF_HOURS_LOG, "Logging Sale for Off Hours" + auction.getAuctionId());
	}
}

