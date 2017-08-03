package com.hm.sample.config;

import com.hm.sample.kafka.consumer.KafkaConsumer;
import com.hm.sample.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jerry on 2016/1/25 0025.
 */

@Configuration
public class SampleKafkaConsumer extends KafkaConsumer {
    @Autowired
    TestService testService;

    public TestService getSmsService() {
        return testService;
    }

    public void setSmsService(TestService testService) {
        this.testService = testService;
    }
}
