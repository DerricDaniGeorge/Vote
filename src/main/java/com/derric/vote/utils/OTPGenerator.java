package com.derric.vote.utils;

public class OTPGenerator {
	public String generateOTP(int noOfDigits) {
		String otp="";
		for(int i=0;i<noOfDigits;i++) {
			otp+=(int)(Math.random()*10);
		}
		System.out.println(otp);
		return otp;
	}
}
