package com.example.controllertest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import org.apache.commons.io.IOUtils;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
public class StudentcontrollerTest {

	@Autowired
	private EmployeeController studentcontroller;

	@MockBean
	IEmployeerepository studentrepo;

	@Autowired
	@InjectMocks
	EmployeService studentservice;

	@Autowired
	EntityManager em;
	
	
	@Autowired
	private ObjectMapper mapper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getalltest() {

		Employee student = null;

		try {

			String studentResp = IOUtils
					.toString(getClass().getClassLoader().getResourceAsStream("testdata/response.json"), "UTF-8");

			student = mapper.readValue(studentResp, new TypeReference<Employee>() {
			});

			List<Employee> list = new ArrayList<Employee>();
			list.add(student);

			when(studentrepo.findAll()).thenReturn(list);

			ResponseEntity<Object> response = studentcontroller.GetAllEmployees();

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	@Test
	public void findByIdTest() {

		Employee student = null;

		try {

			String studentResp = IOUtils
					.toString(getClass().getClassLoader().getResourceAsStream("testdata/response.json"), "UTF-8");

			student = mapper.readValue(studentResp, new TypeReference<Employee>() {
			});

			

			when(studentrepo.findById(1).get()).thenReturn(student);

			ResponseEntity<Object> response = studentcontroller.GetEmployeeById(1);

			assertNotNull(response);

			assertEquals(HttpStatus.OK, response.getStatusCode());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	@Test
	public void deleteById() {

	
		try {

			
			
		} catch (Exception e) {

			e.printStackTrace();
			}

	}	
}
