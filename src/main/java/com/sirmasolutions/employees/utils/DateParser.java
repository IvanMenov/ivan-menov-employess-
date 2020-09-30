package com.sirmasolutions.employees.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


public class DateParser {
		
	public static LocalDateTime parseDateToLocalDateTime(String date, boolean isDateFrom) {	
		LocalDateTime localDateTime = null;
		boolean parsed = false;
		//try parsing it to LocalDateTime with the given date formatters
		for (DateTimeFormatter dateTimeFormatter : DateTimeFormatPatterns.getFormatPatterns()) {
			if(parsed) {
				break;
			}
			try {
				localDateTime= LocalDateTime.parse(date, dateTimeFormatter);
				parsed = true;
			} catch (Exception e) {}
			
		}
		//try parsing it to LocalDateTime from timestamp if still not parsed
		try {
			if(localDateTime == null && Long.valueOf(date) instanceof Long) {
				localDateTime = getDateTimeFromTimezone(date); 
			}
		} catch (Exception e) {}
		
		//try parsing it to LocalDate
		if(localDateTime == null) {
			LocalDate ld = parseDateToLocalDate(date);
			return (ld == null) ? localDateTime : isDateFrom ? ld.atStartOfDay() : ld.atTime(23, 59, 59);
		}
		
		return localDateTime;
	}
	
	private static LocalDate parseDateToLocalDate(String date) {
		LocalDate localDate = null;
		boolean parsed = false;
		for (DateTimeFormatter dateTimeFormatter : DateTimeFormatPatterns.getFormatPatterns()) {
			if (parsed) {
				break;
			}
			try {
				localDate = LocalDate.parse(date, dateTimeFormatter);
				parsed = true;
			} catch (Exception e) {}
		}
		try {
			if(localDate == null && Long.valueOf(date) instanceof Long) {
				localDate = getDateTimeFromTimezone(date).toLocalDate(); 
			}
		} catch (Exception e) {}
		return localDate;
	}

	private static LocalDateTime getDateTimeFromTimezone(String date) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli( Long.valueOf(date)), 
		         TimeZone.getDefault().toZoneId());
	}

}
