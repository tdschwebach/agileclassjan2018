package com.ebaby.model;

import java.util.LinkedList;
import java.util.List;

public class FeeProcessorFactory
{
	private static List<FeeProcessor> feeProcessors;
	
	public static FeeProcessor getInstance(Auction auction) {
		LuxuryTaxFeeProcessor luxuryTaxFeeProcessor = new LuxuryTaxFeeProcessor(null);
		ShippingFeeProcessor shippingFeeProcessor = new ShippingFeeProcessor(luxuryTaxFeeProcessor);
		
		SellerFeeProcessor sellerFeeProcessor = new SellerFeeProcessor(shippingFeeProcessor);
		//Not sure what to return here....
		//I think this is right.  Just return the first item in the chain. -WV
		return sellerFeeProcessor;
	}
}
