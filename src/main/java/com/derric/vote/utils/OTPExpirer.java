package com.derric.vote.utils;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;

public class OTPExpirer {
	private long expireAfter;
	
	public OTPExpirer(long expireAfter) {
		this.expireAfter=expireAfter;
	}
	
	public void expireOTP(String OTP,WebRequest request,SessionAttributeStore store) {
		Timer timer=new Timer();
		TimerTask expirationTask=new TimerTask() {
			public void run() {
				store.cleanupAttribute(request, "otp");
				System.out.println("OTP expired");
			}
		};
		timer.schedule(expirationTask, expireAfter);
		
	}
}
