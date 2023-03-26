package com.oop.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException{
	public  AppException() {
		super();
	}
	
	public  AppException(String message) {
		super(message);
	}
}