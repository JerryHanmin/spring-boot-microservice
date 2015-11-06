package com.hm.sample;

import com.hm.sample.model.Message;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
public class KafkaProducerApp {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducerApp.class);

    @Autowired
    private Environment env;
    @Autowired
    private Producer<String, String> producer;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(KafkaProducerApp.class, args);
    }


    @RequestMapping("/")
    public String home() {
        return "kafka producer";
    }

    @RequestMapping("/sendSampleMsg/{times}")
    public void sendSampleMsg(@PathVariable Integer times) {
        if (times > 0) {
            for (int i = 0; i < times; i++) {
                Message message = new Message();
                message.setName("message " + i);
                message.setContent("kafka sample message " + i);

                sendSampleMessageToKafka(message);
            }
        }
    }


    private void sendSampleMessageToKafka(Message message) {
        try {
            producer.send(new KeyedMessage<>(env.getProperty("kafka.sample.topic"), JSONObject.fromObject(message).toString()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
