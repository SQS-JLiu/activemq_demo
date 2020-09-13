package com.example.activemq_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivemqDemoApplication {
	private final Logger logger = LoggerFactory.getLogger(ActivemqDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ActivemqDemoApplication.class, args);
	}

}
