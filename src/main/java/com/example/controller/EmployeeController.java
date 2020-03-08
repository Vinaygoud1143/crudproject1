package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.service.EmployeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class EmployeeController {

	@Autowired
	EmployeService employeeservice;

	@ApiOperation("this is api operation for getting the all employee details")
	@GetMapping("/employees/getall")
	public ResponseEntity<Object> GetAllEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		try {
			list = employeeservice.getall();
			if (list.size() == 0) {
				throw new EmployeeException("no employee records  found please add the records");
			}
			
		} catch (EmployeeException e) {
			return new ResponseEntity<Object>(e.message, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(list, HttpStatus.OK);
	}

	@ApiOperation("this api for getting particular employee deatils")
	@GetMapping(value = "/employee/{id}")
	public ResponseEntity<Object> GetEmployeeById(
			@ApiParam(value = "give id to get employee details", required = true) @PathVariable Integer id)

	{

		Employee entity = null;
		try {

			entity = employeeservice.findemployee(id);

		} catch (EmployeeException ex) {
			return new ResponseEntity<Object>(ex.message, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<Object>(entity, HttpStatus.OK);

	}

	@ApiOperation("this api for update the employee details or create employee  if employee not exist ")
	@PostMapping("/employees")
	public ResponseEntity<Object> CreateOrUpdateEmployee(
			@ApiParam(value = "give employee detals", required = true) @RequestBody Employee employee) {

		Employee updated = null;
		try {
			updated = employeeservice.updatesaveemployee(employee);
		} catch (Exception e) {

			return new ResponseEntity<Object>("please give correct employee details", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(updated, HttpStatus.OK);
	}

	@ApiOperation("this api for deleting employee for given  id")
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<String> DeleteEmployeeById(
			@ApiParam(value = "please give employee id ", required = true) @PathVariable("id") Integer id) {

		try {
			employeeservice.deleteEmployeeById(id);

			return new ResponseEntity<String>("deleted succesufully employee record", HttpStatus.OK);
		} catch (EmployeeException e) {
			return new ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND);
		}
	}

}
