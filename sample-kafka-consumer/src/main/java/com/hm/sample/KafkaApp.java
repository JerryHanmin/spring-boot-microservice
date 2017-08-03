package com.hm.sample;

import com.hm.sample.avro.AvroMessage;
import com.hm.sample.config.SampleKafkaConsumer;
import com.hm.sample.kafka.KafkaTopicBean;
import com.hm.sample.kafka.KafkaUtils;
import com.hm.sample.kafka.consumer.KafkaConsumer;
import com.hm.sample.kafka.consumer.KafkaConsumerExecutorParam;
import com.hm.sample.kafka.consumer.KafkaConsumerThreadParam;
import com.hm.sample.service.SampleKafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class KafkaApp {

    private final static Logger logger = LoggerFactory.getLogger(KafkaApp.class);

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(KafkaApp.class)
                .run(args);

        KafkaConsumer kafkaConsumer = context.getBean("smsKafkaConsumer", SampleKafkaConsumer.class);
        List<KafkaTopicBean> consumerTopics = kafkaConsumer.getKafkaConsumerBean().getTopics();

        List<KafkaTopicBean> topics = new ArrayList<>();
        topics.addAll(consumerTopics);

        //自动创建topic
        KafkaUtils.createTopics(topics, kafkaConsumer.getKafkaConsumerBean().getZookeeper());

        AvroMessage avroMessage = context.getBean("avroMessage", AvroMessage.class);

        List<KafkaConsumerExecutorParam> params = new ArrayList<>();

        for (KafkaTopicBean topic : consumerTopics) {
            KafkaConsumerExecutorParam param = new KafkaConsumerExecutorParam();
            KafkaConsumerThreadParam threadParam = new KafkaConsumerThreadParam();
            param.setKafkaTopic(topic);
            if (topic.getName().equals(avroMessage.getSendSmsSchema().getName())) {
                threadParam.setSchema(avroMessage.getSendSmsSchema());
                threadParam.setThread(SampleKafkaService.class);
                threadParam.setKafkaConsumer(kafkaConsumer);
                param.setThread(threadParam);
                params.add(param);
            }
        }

        if(params.size() > 0)
            kafkaConsumer.executor(params);
        else
            logger.info("没有需要监听的topic !");
    }


}
