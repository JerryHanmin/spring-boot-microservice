package com.hm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableEurekaClient
public class HystrixDashboardApp {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(HystrixDashboardApp.class, args);
	}
}
