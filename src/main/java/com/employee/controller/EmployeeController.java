package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.exception.EmployeeException;
import com.employee.service.EmployeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class EmployeeController {

	@Autowired
	EmployeService employeeService;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@ApiOperation("This API Operation is useful for getting the details of all employees")
	@GetMapping("/getAllEmployees")
	public ResponseEntity<Object> getListOfEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		logger.info("Get all employee details from datastore");
		try {
			employeeList = employeeService.getAllEmployees();

			if (CollectionUtils.isEmpty(employeeList)) {
				throw new EmployeeException(
						"There are no employee records found in the database. Please add the records.");
			}

		} catch (EmployeeException employeeException) {
			return new ResponseEntity<Object>(employeeException.message, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(employeeList, HttpStatus.OK);
	}

	@ApiOperation("This API is useful to get particular employee deatils based on id")
	@GetMapping(value = "/get/{id}/employee")
	public ResponseEntity<Object> getEmployeeById(
			@ApiParam(value = "give id to get employee details", required = true) @PathVariable Integer id)

	{

		Employee entity = null;
		logger.info("Get an employee details from datastore based on user given id" + id);
		try {

			entity = employeeService.findEmployee(id);

		} catch (EmployeeException ex) {
			return new ResponseEntity<Object>(ex.message, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<Object>(entity, HttpStatus.OK);

	}

	@ApiOperation("this api is useful for update the employee details or create employee  if employee not exist ")
	@PostMapping("/createOrUpdate")
	public ResponseEntity<Object> createOrUpdateEmployee(
			@ApiParam(value = "give employee detals", required = true) @RequestBody Employee employee)
			throws EmployeeException {

		String createdOrUpdated = null;
		ResponseEntity<Object> responseEntity = null;
		logger.info("Creates / Updates Employee Details");

		if (null == employee) {
			throw new EmployeeException("Employee Object / Request Body missing");
		}
		
		try {
			createdOrUpdated = employeeService.updateOrSaveEmployee(employee);

			if (createdOrUpdated.equals("Created")) {
				responseEntity = new ResponseEntity<Object>(createdOrUpdated, HttpStatus.CREATED);
			} else {
				responseEntity = new ResponseEntity<Object>(createdOrUpdated, HttpStatus.OK);
			}
		} catch (Exception exception) {

			responseEntity = new ResponseEntity<Object>("please give correct employee details",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@ApiOperation("this api for deleting employee for given  id")
	@DeleteMapping("/delete/{id}/employee")
	public ResponseEntity<String> deleteEmployeeById(
			@ApiParam(value = "please give employee id ", required = true) @PathVariable("id") Integer id) {

		logger.info("Deletes an employee based on given id"+id);
		
		try {
			employeeService.deleteEmployeeById(id);

			return new ResponseEntity<String>("deleted succesufully employee record", HttpStatus.OK);
		} catch (EmployeeException e) {
			return new ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND);
		}
	}

}
