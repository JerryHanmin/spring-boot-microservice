package com.hm.demo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Demo2App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Demo2App.class, args);
	}

}
