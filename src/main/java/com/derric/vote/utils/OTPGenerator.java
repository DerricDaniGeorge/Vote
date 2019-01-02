package com.derric.vote.utils;

public class OTPGenerator {
	private int noOfDigits;
	public OTPGenerator(int noOfDigits) {
		this.noOfDigits=noOfDigits;
	}
	public String generateOTP() {
		String otp="";
		for(int i=0;i<noOfDigits;i++) {
			otp+=(int)(Math.random()*10);
		}
		System.out.println(otp);
		return otp;
	}
}
