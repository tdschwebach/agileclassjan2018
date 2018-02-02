package com.ebaby.model;

import java.util.LinkedList;
import java.util.List;

public class FeeProcessorFactory
{
	private static List<FeeProcessor> feeProcessors;
	
	public static FeeProcessorFactory getInstance(Auction auction) {
		LuxuryTaxFeeProcessor luxuryTaxFeeProcessor = new LuxuryTaxFeeProcessor(null);
		ShippingFeeProcessor shippingFeeProcessor = new ShippingFeeProcessor(luxuryTaxFeeProcessor);
		
		SellerFeeProcessor sellerFeeProcessor = new SellerFeeProcessor(shippingFeeProcessor);
		//Not sure what to return here....
		return sellerFeeProcessor;
	}
}
