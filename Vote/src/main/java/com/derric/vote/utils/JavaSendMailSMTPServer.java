//Must turn on Allow less secure apps:ON settings in google to successfully authenticate and sending mail.

package com.derric.vote.utils;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
public class JavaSendMailSMTPServer implements MailSender {
	

	private @Value("${email.username}")String userName;
	private @Value("${email.password}")String password;
	private @Value("${email.subject}")String subject;
	private @Value("${email.content}") String content;
	private Session session;
	private Properties mailServerProperties;
	
	public JavaSendMailSMTPServer(Properties properties) {
	mailServerProperties=properties;
	}
	public void authenticate() {
		session=Session.getDefaultInstance(mailServerProperties,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName,password);
			}
		});
	}
	public void sendOTP(String to,String OTP) {
		authenticate();
		try {
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(userName));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(content+" "+OTP);
		Transport.send(message);
		System.out.println("Message with OTP "+OTP+" sent to "+to);
		}catch(MessagingException me) {
			System.out.println("Message sending failed");
			me.printStackTrace();
		}
	}
}
