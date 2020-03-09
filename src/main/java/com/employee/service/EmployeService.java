package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.exception.EmployeeException;
import com.employee.repository.IEmployeeRepo;

@Service
public class EmployeService {

	@Autowired
	IEmployeeRepo employeeRepo;

	private static final Logger logger = LoggerFactory.getLogger(EmployeService.class);

	public List<Employee> getAllEmployees() throws EmployeeException {
		List<Employee> alllist = null;
		logger.debug("START :::: Get all employee details from datastore");
		try {
			alllist = employeeRepo.findAll();
		} catch (Exception exception) {
			logger.error("Issue while fetching all employee details from datastore" + exception);
			throw new EmployeeException("There are no employees in the datastore");
		}

		return alllist;
	}

	public Employee findEmployee(String employeeNumber) throws EmployeeException {

		Optional<Employee> employeeOptional = Optional.empty();
		logger.debug("START :::: Get an employee details from datastore based on given employeeNumber");
		try {
			employeeOptional = employeeRepo.findById(employeeNumber);

			if (!employeeOptional.isPresent()) {

				throw new EmployeeException("employee is not found with id:" + employeeNumber);
			}
		} catch (Exception exception) {
			logger.error("Issue while fetching an employee details from datastore" + exception);
			throw new EmployeeException(
					"There is no employee in the datastore with given employeeNumber" + employeeNumber);
		}

		return employeeOptional.get();
	}

	public String updateOrSaveEmployee(Employee employee) throws EmployeeException {

		Optional<Employee> employeesOptional = Optional.empty();
		String result = null;

		logger.debug("START :::: Update / Create an employee details in datastore based on given employee details");
		try {
			employeesOptional = employeeRepo.findById(employee.getEmployeeNumber());

			if (employeesOptional.isPresent()) {
				Employee existingEmployee = employeesOptional.get();

				existingEmployee.setEmployeeName(employee.getEmployeeName());
				existingEmployee.setEmployeeAddress(employee.getEmployeeAddress());
				existingEmployee.setDepartmentName(employee.getDepartmentName());
				employeeRepo.save(existingEmployee);
				result = "Updated";
			} else {
				employeeRepo.save(employee);
				result = "Created";
			}
		} catch (Exception exception) {
			logger.error("Issue while creating / updating an employee details in datastore" + exception);
			throw new EmployeeException(
					"Issue while creating or updating the employee details" + employee.getEmployeeNumber());
		}

		return result;
	}

	public void deleteEmployeeById(String employeeNumber) throws EmployeeException {

		Optional<Employee> employeeOptional = Optional.empty();
		logger.debug("START :::: Delete an employee in datastore based on given employee number");

		try {
			employeeOptional = employeeRepo.findById(employeeNumber);

			if (employeeOptional.isPresent()) {
				employeeRepo.deleteById(employeeNumber);
			} else {
				throw new EmployeeException("no employee record found to delete with the id:" + employeeNumber);
			}
		} catch (Exception exception) {
			logger.error("Issue while deleting an employee in datastore based on given employee number" + exception);
			throw new EmployeeException("Issue while deleting the employee details" + employeeNumber);
		}

	}

}
