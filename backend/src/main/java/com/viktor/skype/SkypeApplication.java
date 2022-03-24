package com.viktor.skype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SkypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkypeApplication.class, args);
	}

}
