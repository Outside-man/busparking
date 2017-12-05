package com.dangod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BusparkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusparkingApplication.class, args);
	}
}
