package com.hm.sample.config;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KafkaConsumerGroup implements Runnable {
    private ConsumerConnector consumer;
    private String sampleTopic;
    private int a_numThreads;
    private ExecutorService executor;

    public KafkaConsumerGroup(KafkaConsumerConfiguration kafkaConsumerConfiguration) {
        this.consumer = kafkaConsumerConfiguration.getConsumerConnector();
        this.sampleTopic = kafkaConsumerConfiguration.getSampleTopic();
        this.a_numThreads = kafkaConsumerConfiguration.getNumThreads();
    }

    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(sampleTopic, new Integer(a_numThreads));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(sampleTopic);
        executor = Executors.newFixedThreadPool(a_numThreads);
        int threadNumber = 0;
        for (KafkaStream<byte[], byte[]> stream : streams) {
            executor.submit(new SampleConsumer(stream, threadNumber));
            threadNumber++;
        }

    }
}
