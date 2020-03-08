package com.example.controllertest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.CruddemoApplication;
import com.example.controller.EmployeeController;
import com.example.entity.Employee;
import com.example.irepository.IEmployeerepository;
import com.example.service.EmployeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CruddemoApplication.class)
@TestPropertySource(locations = "classpath:application-mock.properties")
public class Employeecontroller {

	@Autowired
	private EmployeeController employeecontroller;

	@MockBean
	IEmployeerepository employeerepo;

	@Autowired
	@InjectMocks
	EmployeService employeeservice;

	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getalltest() {

		Employee employee = null;

		try {

			String studentResp = IOUtils
					.toString(getClass().getClassLoader().getResourceAsStream("testdata/response.json"), "UTF-8");

			employee = mapper.readValue(studentResp, new TypeReference<Employee>() {
			});

			List<Employee> list = new ArrayList<Employee>();
			list.add(employee);

			when(employeerepo.findAll()).thenReturn(list);

			ResponseEntity<Object> response = employeecontroller.GetAllEmployees();

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	@Test
	public void findByIdTest() {

		Employee employee = null;

		try {

			String studentResp = IOUtils
					.toString(getClass().getClassLoader().getResourceAsStream("testdata/response.json"), "UTF-8");

			employee = mapper.readValue(studentResp, new TypeReference<Employee>() {
			});

			

			when(employeerepo.findById(1).get()).thenReturn(employee);

			ResponseEntity<Object> response = employeecontroller.GetEmployeeById(1);

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	
}
