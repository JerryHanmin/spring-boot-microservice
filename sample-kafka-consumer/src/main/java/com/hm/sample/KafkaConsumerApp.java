package com.hm.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@RestController
@ComponentScan(basePackages = "com.hm.sample")
@EnableEurekaClient
public class KafkaConsumerApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(KafkaConsumerApp.class, args);
    }


    @RequestMapping("/")
    public String home() {
        return "kafka consumer";
    }


}
