package com.hm.sample.service;

import com.hm.sample.config.SampleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(SampleBean.class)
@SuppressWarnings("all")
public class TestService {
    private final static Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private SampleBean sampleBean;

    public String getSampleBean() {
        return sampleBean.getClass().getName();
    }
}
