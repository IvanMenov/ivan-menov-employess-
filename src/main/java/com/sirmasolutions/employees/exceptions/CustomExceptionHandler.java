package com.sirmasolutions.employees.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
				body(new ResponseMessage("File too large!",ex.getCause()));
	}
	@ExceptionHandler(DateNotParsedException.class)
	public ResponseEntity<ResponseMessage> handleDateNotParsedException(DateNotParsedException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
				body(new ResponseMessage(ex.getMessage(),ex.getCause()));
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
				body(new ResponseMessage("Something went wrong!",null));
		
	}
	
	private class ResponseMessage {
		String message;
		Throwable cause; 
		
		public ResponseMessage(String message, Throwable throwable) {
			this.message = message;
			this.cause = throwable;
		}

		public String getMessage() {
			return message;
		}

		public Throwable getCause() {
			return cause;
		}

	}
}
