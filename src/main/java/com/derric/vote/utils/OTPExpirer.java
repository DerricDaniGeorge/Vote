package com.derric.vote.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.WebRequest;

public class OTPExpirer {
	
	private long expireAfter;
	private Timer timer;
	
	public OTPExpirer(long expireAfter) {
		this.expireAfter=expireAfter;
	}
	
	public void expireOTP(HttpSession session,String otp) {
		timer=new Timer();
		TimerTask expirationTask=new TimerTask() {
			public void run() {
				System.out.println("otp in session before clearing: "+session.getAttribute("otp"));
				session.setAttribute("otp",null);
				System.out.println("OTP expired. So otp in session is now: "+session.getAttribute("otp"));
			}
		};
		timer.schedule(expirationTask, expireAfter);
		
	}
	public void expireOTPNow(HttpSession session) {
		System.out.println("Inside expireOTPNow(),otp in session : "+session.getAttribute("otp"));
		session.setAttribute("otp", null);
		cancelTimer();
	}
	
	public void cancelTimer() {
		timer.cancel();
		System.out.println("Timer cancelled");
	}
}
