package com.employee.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.employee.EmployeeApplication;
import com.employee.entity.Employee;
import com.employee.repository.IEmployeeRepo;
import com.employee.service.EmployeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeApplication.class)
@TestPropertySource(locations = "classpath:application-mock.properties")
public class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;

	@Mock
	IEmployeeRepo employeeRepo;

	@Autowired
	@InjectMocks
	EmployeService employeeService;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
		ReflectionTestUtils.setField(employeeService, "employeeRepo", employeeRepo);

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

			when(employeeRepo.findAll()).thenReturn(list);

			ResponseEntity<Object> response = employeeController.getListOfEmployees();

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

			when(employeeRepo.findById("1").get()).thenReturn(employee);

			ResponseEntity<Object> response = employeeController.getEmployeeById("1");

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	@Test
	public void saveupdateTest() {

		Employee employee = null;

		try {

			String studentResp = IOUtils
					.toString(getClass().getClassLoader().getResourceAsStream("testdata/response.json"), "UTF-8");

			employee = mapper.readValue(studentResp, new TypeReference<Employee>() {
			});

			when(employeeRepo.findById("102").get()).thenReturn(employee);

			ResponseEntity<Object> response = employeeController.getEmployeeById("102");

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}