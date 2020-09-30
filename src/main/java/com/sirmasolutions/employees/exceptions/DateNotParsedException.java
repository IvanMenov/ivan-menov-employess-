package com.sirmasolutions.employees.exceptions;

public class DateNotParsedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2565010991338018513L;
	
	public DateNotParsedException() {
		super("Not parsable dates");
	}
	public DateNotParsedException(String date) {
		super(String.format("Unable to parse the following date: %s",date));
	}
}
