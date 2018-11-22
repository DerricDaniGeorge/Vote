package com.derric.vote.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter; //Depricated
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.derric.vote.formatter.StringToLocalDateFormatter;
import com.derric.vote.utils.OTPGenerator;
import com.derric.vote.utils.StringUtils;
import com.derric.vote.validators.RegisterUserValidator;

//import com.derric.vote.filters.RegisterFormFilter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")

public class WebConfiguration implements WebMvcConfigurer{
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
		return new OTPGenerator();
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
