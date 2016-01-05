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
public class FeignClient2App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(FeignClient2App.class, args);
	}

	@Autowired
	FeignClient1 feignClient1;

	@FeignClient("feign-client1")
	public interface FeignClient1 {
		@RequestMapping(method = RequestMethod.GET, value = "/getClient1")
		String getClient1();
	}


	@RequestMapping(method = RequestMethod.GET, value = "/getClient1")
	public String getClient1(){
		return feignClient1.getClient1();
	}


	@RequestMapping(method = RequestMethod.GET, value = "/getClient2")
	public String getClient2(){
		return "This is Client2 !";
	}
}
