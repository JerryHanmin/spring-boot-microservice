package com.hm.sample;

import com.hm.sample.model.Message;
import com.hm.sample.model.MessageRequest;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@Configuration
@EnableAutoConfiguration
@RestController
@ComponentScan(basePackages = "com.hm.sample")
@EnableEurekaClient
public class KafkaProducerApp {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducerApp.class);

    @Autowired
    private Environment env;
    @Autowired
    KafkaProducerConfiguration kafkaProducerConfiguration;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(KafkaProducerApp.class, args);
    }


    @RequestMapping("/")
    public String home() {
        return "kafka producer";
    }

    @RequestMapping(value = "/sendMsg/{times}", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public void sendSampleMsg(@PathVariable Integer times) {
        if (times > 0) {
            for (int i = 0; i < times; i++) {
                Message message = new Message();
                message.setName("message " + i);
                message.setContent("kafka sample message " + i);
                message.setCreated_time(System.currentTimeMillis());

                sendSampleMessageToKafka(message);
            }
        }
    }

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public void sendSampleMsg(@RequestBody MessageRequest messageRequest) {
        Message message = new Message();
        message.setName(messageRequest.getName());
        message.setContent(messageRequest.getContent());
        message.setCreated_time(System.currentTimeMillis());
        sendSampleMessageToKafka(message);
    }


    private void sendSampleMessageToKafka(Message message) {
        try {
            kafkaProducerConfiguration.getProducer().send(new KeyedMessage<>(kafkaProducerConfiguration.getSampleTopic(), JSONObject.fromObject(message).toString().getBytes()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
