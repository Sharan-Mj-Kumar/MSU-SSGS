package com.example.SSGS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.SSGS"})
public class SsgsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgsApplication.class, args);
	}

}