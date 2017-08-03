package com.hm.sample.kafka.consumer;


import com.hm.sample.kafka.KafkaTopicBean;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiongtao
 */
@EnableConfigurationProperties(KafkaConsumerBean.class)
public class KafkaConsumer {

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    KafkaConsumerBean kafkaConsumerBean;

    @Bean
    public ConsumerConfig getConsumerConfig() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", kafkaConsumerBean.getZookeeper().getConnect());
        properties.put("group.id", kafkaConsumerBean.getGroup());
        properties.put("zookeeper.session.timeout.ms", kafkaConsumerBean.getZookeeper().getSessionTimeout());
        properties.put("zookeeper.connection.timeout.ms", kafkaConsumerBean.getZookeeper().getConnectTimeOut());
        properties.put("zookeeper.sync.time.ms", kafkaConsumerBean.getZookeeper().getSynctime());
        properties.put("auto.commit.interval.ms", kafkaConsumerBean.getInterval());
        properties.put("auto.offset.reset", kafkaConsumerBean.getOffsetReset());
        properties.put("serializer.class", kafkaConsumerBean.getSerializer());
        return new ConsumerConfig(properties);
    }

    @Bean
    public ConsumerConnector consumerConnector() {
        return kafka.consumer.Consumer.createJavaConsumerConnector(getConsumerConfig());
    }

    @Bean
    public KafkaConsumerBean getKafkaConsumerBean() {
        return this.kafkaConsumerBean;
    }

    public void executor(List<KafkaConsumerExecutorParam> params) throws Exception {

        Map<String, Integer> topicCountMap = new HashMap<>();

        for (KafkaConsumerExecutorParam param : params) {
            KafkaTopicBean kafkaTopic = param.getKafkaTopic();
            topicCountMap.put(kafkaTopic.getName(), kafkaTopic.getPartitions());
        }

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector().createMessageStreams(topicCountMap);

        for (KafkaConsumerExecutorParam param : params) {
            KafkaTopicBean kafkaTopic = param.getKafkaTopic();
            Class kafkaConsumerThread = Class.forName(param.getThread().getThread().getName());
            //取得全部的构造函数
            Constructor<?> tcons[] = kafkaConsumerThread.getConstructors();


            List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(kafkaTopic.getName());
            ExecutorService executor = Executors.newFixedThreadPool(kafkaTopic.getPartitions());

            int num = 0;
            for (KafkaStream stream : streams) {
                num++;
                logger.debug("启动topic [{}] 的第{}个线程", kafkaTopic.getName(), num);
                executor.submit((KafkaConsumerThread) tcons[0].newInstance(stream, param.getThread().getSchema(), param.getThread().getKafkaConsumer()));
            }
        }

    }
}