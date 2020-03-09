package com.employee.controller;

import java.util.ArrayList;
import java.util.List;

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

	@ApiOperation("This API Operation is useful for getting the details of all employees")
	@GetMapping("/get/employees")
	public ResponseEntity<Object> getListOfEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
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
	@GetMapping(value = "/employee/{id}")
	public ResponseEntity<Object> getEmployeeById(
			@ApiParam(value = "give id to get employee details", required = true) @PathVariable Integer id)

	{

		Employee entity = null;
		try {

			entity = employeeService.findEmployee(id);

		} catch (EmployeeException ex) {
			return new ResponseEntity<Object>(ex.message, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<Object>(entity, HttpStatus.OK);

	}

	@ApiOperation("this api for update the employee details or create employee  if employee not exist ")
	@PostMapping("/employees")
	public ResponseEntity<Object> createOrUpdateEmployee(
			@ApiParam(value = "give employee detals", required = true) @RequestBody Employee employee) {

		Employee updated = null;
		try {
			updated = employeeService.updateOrSaveEmployee(employee);
		} catch (Exception e) {

			return new ResponseEntity<Object>("please give correct employee details", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(updated, HttpStatus.OK);
	}

	@ApiOperation("this api for deleting employee for given  id")
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<String> deleteEmployeeById(
			@ApiParam(value = "please give employee id ", required = true) @PathVariable("id") Integer id) {

		try {
			employeeService.deleteEmployeeById(id);

			return new ResponseEntity<String>("deleted succesufully employee record", HttpStatus.OK);
		} catch (EmployeeException e) {
			return new ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND);
		}
	}

}
