package com.example.irepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.entity.Employee;

@Repository
public interface IEmployeerepository extends JpaRepository<Employee, Integer> {

}
