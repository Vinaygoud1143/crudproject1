package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.exception.EmployeeException;
import com.example.irepository.IEmployeerepository;

@Service
public class EmployeService {

	@Autowired
	IEmployeerepository employeerepo;

	public List<Employee> getall() {
		List<Employee> alllist = new ArrayList<Employee>();
	
		alllist = employeerepo.findAll();
		
		return alllist;
	}

	public Employee findemployee(Integer id) throws EmployeeException {
		Optional<Employee> employee = employeerepo.findById(id);
		
		if(!employee.isPresent()) {
			
			throw new EmployeeException("employee is not found with id:"+id);
		}
		Employee record = employee.get();
		return record;
	}

	public Employee updatesaveemployee(Employee employee) {
		Optional<Employee> employees = employeerepo.findById(employee.getEno());
		if (employees.isPresent()) {
			Employee employee1 = employees.get();

			employee1.setEname(employee.getEname());
			employee1.setEaddress(employee.getEaddress());
			employee1.setEdepartment(employee.getEdepartment());
			employee1 = employeerepo.save(employee);
			return employee1;
		} else {
			Employee newemployee = employeerepo.save(employee);
			return newemployee;
		}
	}

	public void deleteEmployeeById(Integer id) throws EmployeeException {
		Optional<Employee> employee = employeerepo.findById(id);

		if (employee.isPresent()) {
			employeerepo.deleteById(id);
		} 
		else {
            throw new EmployeeException("no employee record found to delete with the id:"+id);
		}

	}
	
}
