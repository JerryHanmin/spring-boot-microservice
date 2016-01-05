package com.hm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class FeignClient1App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FeignClient1App.class, args);
	}

	@Autowired
	FeignClient2 feignClient2;

	@FeignClient("feign-client2")
	public interface FeignClient2 {
		@RequestMapping(method = RequestMethod.GET, value = "/getClient2")
		String getClient2();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getClient2")
	public String getClient2(){
		return feignClient2.getClient2();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getClient1")
	public String getClient1(){
		return "This is Client1 !";
	}
}
