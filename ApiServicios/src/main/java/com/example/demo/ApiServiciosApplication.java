package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class ApiServiciosApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ApiServiciosApplication.class, args);
	}

}
