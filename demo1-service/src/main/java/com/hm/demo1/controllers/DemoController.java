package com.hm.demo1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jerry on 2015/8/5 0005.
 */
@RestController
@RequestMapping(value = "/demo1")
public class DemoController {
    @Autowired
    private Environment environment;
    @Autowired
    private ConfigurableEnvironment configurableEnvironment;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/test")
    public String demo1(){
        return "demo1";
    }

    @RequestMapping(value = "findDemo2")
    public String findDemo2() {
        List<ServiceInstance> list = discoveryClient.getInstances("demo2");
        String uri = "";
        for (ServiceInstance serviceInstance : list) {
            uri += serviceInstance.getUri();
        }
        return uri;
    }

    @RequestMapping(value = "getEvns")
    public String[] getEvns(){
        String[] envs = environment.getDefaultProfiles();
        return envs;
    }
    @RequestMapping(value = "getConfigEvns")
    public void getConfigEvns(){
        configurableEnvironment.getPropertySources().forEach(
                propertySource -> {
                    if (propertySource.getName().equals("bootstrap")){
                        System.out.println(propertySource.getProperty("eureka.test"));
                    }
                }
        );

    }
}
