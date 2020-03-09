package com.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.exception.EmployeeException;
import com.employee.repository.IEmployeeRepo;

@Service
public class EmployeService {

	@Autowired
	IEmployeeRepo employeeRepo;

	public List<Employee> getAllEmployees() {
		List<Employee> alllist = new ArrayList<Employee>();

		alllist = employeeRepo.findAll();

		return alllist;
	}

	public Employee findEmployee(String employeeNumber) throws EmployeeException {
		Optional<Employee> employee = employeeRepo.findById(employeeNumber);

		if (!employee.isPresent()) {

			throw new EmployeeException("employee is not found with id:" + employeeNumber);
		}
		Employee record = employee.get();
		return record;
	}

	public String updateOrSaveEmployee(Employee employee) {
		Optional<Employee> employees = employeeRepo.findById(employee.getEmployeeNumber());
		String result = null;
		
		if (employees.isPresent()) {
			Employee existingEmployee = employees.get();

			existingEmployee.setEmployeeName(employee.getEmployeeName());
			existingEmployee.setEmployeeAddress(employee.getEmployeeAddress());
			existingEmployee.setDepartmentName(employee.getDepartmentName());
			employeeRepo.save(existingEmployee);
			result = "Updated";
		} else {
			employeeRepo.save(employee);
			result = "Created";
		}
		return result;
	}

	public void deleteEmployeeById(String employeeNumber) throws EmployeeException {
		Optional<Employee> employee = employeeRepo.findById(employeeNumber);

		if (employee.isPresent()) {
			employeeRepo.deleteById(employeeNumber);
		} else {
			throw new EmployeeException("no employee record found to delete with the id:" + employeeNumber);
		}

	}

}
