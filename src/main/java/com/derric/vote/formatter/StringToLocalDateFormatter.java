package com.derric.vote.formatter;

import org.springframework.format.Formatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StringToLocalDateFormatter implements Formatter<LocalDate>{
	private DateTimeFormatter dateTimeFormatter;
	public StringToLocalDateFormatter(String datePattern) {
		this.dateTimeFormatter=DateTimeFormatter.ofPattern(datePattern);
	}
	public String print(LocalDate date, Locale locale) {
		return date.format(dateTimeFormatter);
	}
	public LocalDate parse(String s, Locale locale) {
		return LocalDate.parse(s, dateTimeFormatter);
	}
}
