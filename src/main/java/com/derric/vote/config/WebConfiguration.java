package com.derric.vote.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; //Depricated
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.derric.vote.dao.IUserDBService;
import com.derric.vote.dao.UserDBService;
import com.derric.vote.formatter.StringToLocalDateFormatter;
import com.derric.vote.services.UserServices;
import com.derric.vote.utils.JavaSendMailSMTPServer;
import com.derric.vote.utils.MailSender;
import com.derric.vote.utils.OTPExpirer;
import com.derric.vote.utils.OTPGenerator;
import com.derric.vote.utils.StringUtils;
import com.derric.vote.validators.RegisterUserValidator;

//import com.derric.vote.filters.RegisterFormFilter;

@Configuration
@EnableWebMvc
@PropertySources({@PropertySource("classpath:application.properties"),@PropertySource("classpath:db.properties")})

public class WebConfiguration implements WebMvcConfigurer{
	
	private @Value("${email.server.host}")String host;
	private @Value("${email.smtp.port}")int port;

	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver=new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
/*	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(registerFormFilter());
	}
	@Bean
	public RegisterFormFilter registerFormFilter() {
		return new RegisterFormFilter();
	}  */
	
	@Bean
	public RegisterUserValidator registerUserValidator() {
		return new RegisterUserValidator();
	}
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
		messageSource.setBasename("errorTexts");
		return messageSource;
	}
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new StringToLocalDateFormatter("yyyy-MM-dd"));
	}
	
	@Bean
	@Lazy(true)
	public StringUtils stringUtils() {
		return new StringUtils();
	}
	@Bean
	@Lazy(true)
	public OTPGenerator otpGenerator() {
		return new OTPGenerator(6);
	}
	@Bean
	@Lazy(true)
	public OTPExpirer otpExpirer() {
		return new OTPExpirer(20*1000);
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
	@Lazy(true)
	public MailSender mailSender() {
		Properties mailServerProperties=new Properties();
		mailServerProperties.put("mail.smtp.host",host);
		mailServerProperties.put("mail.smtp.auth",true);
		mailServerProperties.put("mail.smtp.starttls.enable",true);
		mailServerProperties.put("mail.smtp.port", port);
		return new JavaSendMailSMTPServer(mailServerProperties);
	}
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource=new BasicDataSource();
		basicDataSource.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/vote");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("admin");
		basicDataSource.setInitialSize(2);
		basicDataSource.setMaxTotal(5);
		return basicDataSource;
	}
	
	@Bean
	public IUserDBService userDAO() {
		return new UserDBService(jdbcTemplate());
	}
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	@Bean
	public UserServices userServices() {
		return new UserServices();
	} 
}
