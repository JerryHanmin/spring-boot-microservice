package com.hm.demo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jerry on 2015/8/5 0005.
 */
@RestController
@RequestMapping(value = "/demo2")
public class DemoController {
    @Autowired
    private Environment environment;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping
    public String demo2() {
        return "demo2";
    }

    @RequestMapping(value = "/findDemo1")
    public String findDemo1() {
        List<ServiceInstance> list = discoveryClient.getInstances("apps-demo1");
        String uri = "";
        for (ServiceInstance serviceInstance : list) {
            uri += serviceInstance.getUri();
        }
        return uri;
    }

}
