package com.hm.sample.kafka.consumer;


import com.hm.sample.kafka.KafkaTopicBean;

/**
 * Created by Jerry on 2016/1/25 0025.
 */
public class KafkaConsumerExecutorParam {
    private KafkaTopicBean kafkaTopic;
    private KafkaConsumerThreadParam thread;

    public KafkaTopicBean getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(KafkaTopicBean kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public KafkaConsumerThreadParam getThread() {
        return thread;
    }

    public void setThread(KafkaConsumerThreadParam thread) {
        this.thread = thread;
    }
}
