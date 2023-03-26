package com.oop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookTicketExceptionHandler {

	@ExceptionHandler(value = AppException.class)
	public ResponseEntity<ExceptionInfo> handleAppException(AppException appException) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		String message = appException.getMessage();
		exceptionInfo.setExceptionCode("EX-000784");
		exceptionInfo.setExceptionMessage(message);
		return new ResponseEntity<>(exceptionInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
