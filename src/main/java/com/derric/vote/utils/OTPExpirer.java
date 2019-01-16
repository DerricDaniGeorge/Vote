package com.derric.vote.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;

public class OTPExpirer {
	private long expireAfter;
	
	public OTPExpirer(long expireAfter) {
		this.expireAfter=expireAfter;
	}
	
	public void expireOTP(String OTP,HttpServletRequest request,HttpSession session) {
		Timer timer=new Timer();
		TimerTask expirationTask=new TimerTask() {
			public void run() {
				session.setAttribute("otp","");
				System.out.println("OTP expired");
			}
		};
		timer.schedule(expirationTask, expireAfter);
		
	}
}
