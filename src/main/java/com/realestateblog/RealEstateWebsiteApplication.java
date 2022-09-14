package com.realestateblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages= {"com.realestateblog.*"})
@EnableJpaRepositories(basePackages = "com.realestateblog.repository")
@SpringBootApplication
public class RealEstateWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealEstateWebsiteApplication.class, args);
	}

}
