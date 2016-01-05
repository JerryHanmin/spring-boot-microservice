package com.hm.sample.config;

import kafka.consumer.KafkaStream;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KafkaConsumerGroup implements Runnable {
    private KafkaConsumerConfiguration kafkaConsumerConfiguration;

    private static KafkaConsumerGroup instance = null;

    public static KafkaConsumerGroup getInstance(KafkaConsumerConfiguration kafkaConsumerConfiguration) {
        if (instance == null) {
            synchronized (KafkaConsumerGroup.class) {
                if (instance == null) {
                    instance = new KafkaConsumerGroup(kafkaConsumerConfiguration);
                }
            }
        }
        return instance;
    }

    private KafkaConsumerGroup() {
    }

    private KafkaConsumerGroup(KafkaConsumerConfiguration kafkaConsumerConfiguration) {
        this.kafkaConsumerConfiguration = kafkaConsumerConfiguration;
    }

    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(kafkaConsumerConfiguration.getSampleTopic(), kafkaConsumerConfiguration.getNumThreads());

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = kafkaConsumerConfiguration.getConsumerConnector().createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(kafkaConsumerConfiguration.getSampleTopic());
        ExecutorService executor = Executors.newFixedThreadPool(kafkaConsumerConfiguration.getNumThreads());
        int threadNumber = 0;
        for (KafkaStream<byte[], byte[]> stream : streams) {
            executor.submit(new SampleConsumer(stream, threadNumber,kafkaConsumerConfiguration));
            threadNumber++;
        }

    }
}
