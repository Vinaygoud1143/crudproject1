package com.employee.exception;

public class EmployeeException extends Exception {

	private static final long serialVersionUID = 1L;
	public String message;

	public EmployeeException(String message) {
		super();
		this.message = message;

	}

}
