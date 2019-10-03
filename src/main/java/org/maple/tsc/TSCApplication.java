package org.maple.tsc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class TSCApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TSCApplication.class, args);
	}
}
