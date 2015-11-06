package com.hm.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class Demo1App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Demo1App.class, args);
	}

	@Autowired
	TestClient client;

	public String demoTest() {
		return client.demoTest();
	}
	@FeignClient("demo1service")
	public interface TestClient {

		@RequestMapping(method = RequestMethod.GET, value = "/test")
		String demoTest();

	}
}
