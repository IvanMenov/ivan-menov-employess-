package com.sirmasolutions.employees.utils;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateTimeFormatPatterns {
	private static List<DateTimeFormatter> patterns = Arrays.asList(DateTimeFormatter.BASIC_ISO_DATE,
			DateTimeFormatter.ISO_DATE, DateTimeFormatter.ISO_DATE_TIME, DateTimeFormatter.ISO_INSTANT,
			DateTimeFormatter.ISO_LOCAL_DATE, DateTimeFormatter.ISO_LOCAL_DATE_TIME, DateTimeFormatter.ISO_LOCAL_TIME,
			DateTimeFormatter.ISO_OFFSET_DATE, DateTimeFormatter.ISO_OFFSET_DATE_TIME,
			DateTimeFormatter.ISO_OFFSET_TIME, DateTimeFormatter.ISO_ORDINAL_DATE, DateTimeFormatter.ISO_TIME,
			DateTimeFormatter.ISO_WEEK_DATE, DateTimeFormatter.ISO_ZONED_DATE_TIME,
			DateTimeFormatter.RFC_1123_DATE_TIME);
	
	public static List<DateTimeFormatter> getFormatPatterns(){
		return patterns;
	}
}
