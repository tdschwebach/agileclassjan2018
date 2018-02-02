package com.ebaby.model;

public class Bid
{
	private User bidder;
	private Integer bidAmount;
	
	public Bid(User bidder, Integer bidAmount)
	{
		this.bidder= bidder;
		this.bidAmount = bidAmount;
	}
	
	public User getBidder()
	{
		return bidder;
	}
	
	public void setBidder(User bidder)
	{
		this.bidder = bidder;
	}
	
	public Integer getBidAmount()
	{
		return bidAmount;
	}
	
	public void setBidAmount(Integer bidAmount)
	{
		this.bidAmount = bidAmount;
	}
}
