package com.example.activemq_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class ActivemqDemoApplication {
	private final Logger logger = LoggerFactory.getLogger(ActivemqDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ActivemqDemoApplication.class, args);
	}

}
