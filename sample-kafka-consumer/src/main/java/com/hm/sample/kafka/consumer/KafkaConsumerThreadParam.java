package com.hm.sample.kafka.consumer;

import org.apache.avro.Schema;

/**
 * Created by Jerry on 2016/1/25 0025.
 */
public class KafkaConsumerThreadParam {
    private KafkaConsumer kafkaConsumer;
    private Class thread;
    private Schema schema;

    public KafkaConsumer getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(KafkaConsumer kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public Class getThread() {
        return thread;
    }

    public void setThread(Class thread) {
        this.thread = thread;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
