package com.alonzo.etl.util.ip;

public class TestIPSeeker {

	public static void main(String[] args) {
		IPSeeker ipSeeker = new IPSeeker();
		System.out.println(ipSeeker.getCountry("120.197.87.216"));
		System.out.println(ipSeeker.getCountry("114.61.94.253"));
	}
}
