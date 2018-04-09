package com.alonzo.ae.sdk;
import com.alonzo.ae.sdk.AnalyticsEngineSDK;


public class Test {

	public static void main(String[] args) {
		AnalyticsEngineSDK.onChargeSuccess("orderid123", "alonzo123");
		AnalyticsEngineSDK.onChargeRefund("orderid456", "alonzo456");
	}
	
}
