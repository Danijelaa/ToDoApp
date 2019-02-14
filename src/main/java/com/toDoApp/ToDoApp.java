package com.toDoApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;


@SpringBootApplication
public class ToDoApp extends SpringBootServletInitializer{

	public static void main(String[] args)  {
		SpringApplication.run(ToDoApp.class, args);
	
	}

}
