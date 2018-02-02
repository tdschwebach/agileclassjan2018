package com.ebaby.model;

import com.tobeagile.training.ebaby.services.PostOffice;

import java.util.Date;

public class Auction
{
	private User seller;
	private String itemDescription ;
	private Integer startPrice ;
	private Date startTime ;
	private Date endTime ;
	private AuctionStatus auctionStatus;
	private Bid highBid;
	private Double finalSellerPrice;
	private Double finalBuyerPrice;
	private long auctionId;
	private ItemCategory itemCategory;
	
	public Auction(User seller, String itemDescription, Integer startPrice, Date startTime,
			Date endTime, ItemCategory itemCategory) throws InvalidAuctionException
	{
		if (!seller.isLoggedIn()) {
			throw new InvalidAuctionException("User is not logged in. Can not create auction.");
		}
		
		if (!seller.isSeller()) {
			throw new InvalidAuctionException("User is not a seller. Can not create auction.");
		}
		
		if (startTime.before(new Date(System.currentTimeMillis()))) {
			throw new InvalidAuctionException("Start time is in the past. Can not create auction.");
		}
		
		if (endTime.before(startTime)) {
			throw new InvalidAuctionException("End time is before start time. Can not create auction.");
		}
		
		this.seller = seller;
		this.itemDescription = itemDescription;
		this.startPrice = startPrice;
		this.startTime = startTime;
		this.endTime = endTime;
		this.auctionStatus = AuctionStatus.SCHEDULED;
		this.auctionId = System.currentTimeMillis();
		this.itemCategory = itemCategory;
	}
	
	public User getSeller()
	{
		return seller;
	}
	
	public void setSeller(User seller)
	{
		this.seller = seller;
	}
	
	public String getItemDescription()
	{
		return itemDescription;
	}
	
	public void setItemDescription(String itemDescription)
	{
		this.itemDescription = itemDescription;
	}
	
	public Integer getStartPrice()
	{
		return startPrice;
	}
	
	public void setStartPrice(Integer startPrice)
	{
		this.startPrice = startPrice;
	}
	
	public Date getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	public Date getEndTime()
	{
		return endTime;
	}
	
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	
	public AuctionStatus getAuctionStatus()
	{
		return auctionStatus;
	}
	
	public void setAuctionStatus(AuctionStatus auctionStatus)
	{
		this.auctionStatus = auctionStatus;
	}
	
	public Bid getHighBid()
	{
		return highBid;
	}
	
	public void setHighBid(Bid highBid)
	{
		this.highBid = highBid;
	}
	
	public long getAuctionId()
	{
		return auctionId;
	}
	
	public ItemCategory getItemCategory()
	{
		return itemCategory;
	}
	
	public Double getFinalSellerPrice()
	{
		return finalSellerPrice;
	}
	
	public void setFinalSellerPrice(Double finalSellerPrice)
	{
		this.finalSellerPrice = finalSellerPrice;
	}
	
	public Double getFinalBuyerPrice()
	{
		return finalBuyerPrice;
	}
	
	public void setFinalBuyerPrice(Double finalBuyerPrice)
	{
		this.finalBuyerPrice = finalBuyerPrice;
	}
	
	public void placeBid(Bid bid) throws InvalidBidException 
	{
		if (!bid.getBidder().isLoggedIn()) {
			throw new InvalidBidException("Bidder must be logged in to bid.");
		}
		if (bid.getBidder().getUserName().equals(seller.getUserName()))
		{
			throw new InvalidBidException("Bidder cannot bid on its own item.");
		}
		if (bid.getBidAmount() <= startPrice)
		{
			throw new InvalidBidException("Bidder cannot bid lower than start price.");
		}
		if (this.highBid != null && bid.getBidAmount() <= this.highBid.getBidAmount() )
		{
			throw new InvalidBidException("Bidder cannot bid <= than the highest bid.");
		}
		this.highBid = bid;
		
	}
	
	public void start() {
		if (this.getAuctionStatus() == AuctionStatus.SCHEDULED)
		{
			this.setAuctionStatus(AuctionStatus.STARTED);
		}
	}
	
	public void close()
	{
		if (this.getAuctionStatus() == AuctionStatus.STARTED)
		{
			this.setAuctionStatus(AuctionStatus.FINISHED);
			//Somehow create a chain of fee processors.
			FeeProcessor feeProcessorFactory = FeeProcessorFactory.getInstance(this);
			//Start the chain of fee processors... HOW?
			//This seems to work, too. -WV
			feeProcessorFactory.closeAuction(this);
			
			
			Notification notification = Notification.getInstance(this);
			notification.sendNotifications();
		}
	}
}
