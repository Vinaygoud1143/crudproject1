package com.example.exception;

import org.springframework.http.HttpStatus;

public class EmployeeException  extends Exception{

	public String message;

	
	public EmployeeException(String message){
		super();
		this.message=message;

		
			}
	
	
	
	
}
