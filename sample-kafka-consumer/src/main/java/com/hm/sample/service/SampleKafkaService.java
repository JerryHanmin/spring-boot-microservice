package com.hm.sample.service;

import com.hm.sample.config.SampleKafkaConsumer;
import com.hm.sample.kafka.consumer.KafkaConsumerThread;
import kafka.consumer.KafkaStream;
import net.sf.json.JSONObject;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jerry on 2016/1/25 0025.
 */
public class SampleKafkaService extends KafkaConsumerThread {
    private final static Logger logger = LoggerFactory.getLogger(SampleKafkaService.class);

    public SampleKafkaService(KafkaStream stream, Schema schema, SampleKafkaConsumer smsKafkaConsumer) {
        super(stream, schema, smsKafkaConsumer);
    }

    @Override
    public void consumerService(JSONObject messageJson) {
        SampleKafkaConsumer smsKafkaConsumer = (SampleKafkaConsumer)kafkaConsumer;
        logger.debug("get kafka message : " + messageJson);
    }
}
