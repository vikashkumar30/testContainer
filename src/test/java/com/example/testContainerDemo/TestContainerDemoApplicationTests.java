package com.example.testContainerDemo;

import com.example.testContainerDemo.student.Student;
import com.example.testContainerDemo.student.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class TestContainerDemoApplicationTests {

	@Container
	private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("student")
			.withUsername("postgres")
			.withPassword("postgres");

	@DynamicPropertySource
	public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);

	}

	@Autowired
	private StudentController studentController;

	@Test
	public void testDemo (){
		List <Student> list = studentController.getStudents();
		assertEquals(1, list.size());
		System.out.println("Test Pass");


	}




}
