package com.ebaby.model;

import java.util.List;

public class CloseProcessorFactory
{
	private static List<CloseProcessor> closeProcessors;
	
	public static CloseProcessor getInstance(Auction auction) {
		OffHoursProcessor offHoursProcessor = new OffHoursProcessor(null);
		LargeSaleProcessor largeSaleProcessor = new LargeSaleProcessor(offHoursProcessor);
		CarLogProcessor carLogProcessor = new CarLogProcessor(largeSaleProcessor);
		LuxuryTaxFeeProcessor luxuryTaxFeeProcessor = new LuxuryTaxFeeProcessor(carLogProcessor);
		ShippingFeeProcessor shippingFeeProcessor = new ShippingFeeProcessor(luxuryTaxFeeProcessor);
		SellerFeeProcessor sellerFeeProcessor = new SellerFeeProcessor(shippingFeeProcessor);
		return sellerFeeProcessor;
	}
}
