package com.hm.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
public class RibbonClientApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RibbonClientApp.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    Environment environment;


    @RequestMapping("/")
    public String home() {
        String str = restTemplate.getForObject("http://oauth2.service/security/oauth/authorize?response_type=code&client_id=androidApp&redirect_uri=code", String.class);
        System.out.println(str);
        return restTemplate.getForObject("http://ribbon-server", String.class);
    }

    @RequestMapping(value = "/choose")
    public String choose() {
        ServiceInstance instance = loadBalancerClient.choose("ribbon-server");
        return instance.getHost();
    }

    @RequestMapping(value = "/test")
    public String test() {
        return environment.getProperty("test");
    }

}
