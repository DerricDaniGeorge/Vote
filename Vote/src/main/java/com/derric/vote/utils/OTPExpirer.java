package com.derric.vote.utils;

import java.util.Timer;
import java.util.TimerTask;

public class OTPExpirer {
	private long expireAfter;
	
	public OTPExpirer(long expireAfter) {
		this.expireAfter=expireAfter;
	}
	
	public void expireOTP(String OTP) {
		Timer timer=new Timer();
		TimerTask expirationTask=new TimerTask() {
			public void run() {
				
			}
		};
	}
}
