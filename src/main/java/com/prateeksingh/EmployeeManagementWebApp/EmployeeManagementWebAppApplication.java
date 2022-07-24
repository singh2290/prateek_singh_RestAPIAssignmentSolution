package com.prateeksingh.EmployeeManagementWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmployeeManagementWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementWebAppApplication.class, args);
	}

   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

}
