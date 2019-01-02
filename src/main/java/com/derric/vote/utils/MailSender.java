package com.derric.vote.utils;

public interface MailSender {
 public void authenticate();
 public void sendOTP(String to, String OTP);
}
