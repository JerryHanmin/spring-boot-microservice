package com.hm.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
@EnableSidecar
public class ApiGatewayApp {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiGatewayApp.class, args);
	}

	@RequestMapping("/")
	public String home() {
		return "Api Gateway App";
	}
}
