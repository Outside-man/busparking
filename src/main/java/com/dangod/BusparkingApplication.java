package com.dangod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class BusparkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusparkingApplication.class, args);
	}
}
