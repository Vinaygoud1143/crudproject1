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

	public Employee findEmployee(Integer employeeNumber) throws EmployeeException {
		Optional<Employee> employee = employeeRepo.findById(employeeNumber);

		if (!employee.isPresent()) {

			throw new EmployeeException("employee is not found with id:" + employeeNumber);
		}
		Employee record = employee.get();
		return record;
	}

	public Employee updateOrSaveEmployee(Employee employee) {
		Optional<Employee> employees = employeeRepo.findById(employee.getEmployeeNumber());
		if (employees.isPresent()) {
			Employee employee1 = employees.get();

			employee1.setEmployeeName(employee.getEmployeeName());
			employee1.setEmployeeAddress(employee.getEmployeeAddress());
			employee1.setDepartmentName(employee.getDepartmentName());
			employee1 = employeeRepo.save(employee);
			return employee1;
		} else {
			Employee newemployee = employeeRepo.save(employee);
			return newemployee;
		}
	}

	public void deleteEmployeeById(Integer employeeNumber) throws EmployeeException {
		Optional<Employee> employee = employeeRepo.findById(employeeNumber);

		if (employee.isPresent()) {
			employeeRepo.deleteById(employeeNumber);
		} else {
			throw new EmployeeException("no employee record found to delete with the id:" + employeeNumber);
		}

	}

}
