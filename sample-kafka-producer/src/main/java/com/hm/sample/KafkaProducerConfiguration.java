package com.hm.sample;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * 
 * @author xiongtao
 *
 */
@Configuration
public class KafkaProducerConfiguration {
	@Autowired
	private Environment environment;

	@Bean
	public Properties getProperties() {
		Properties props = new Properties();
		props.put("metadata.broker.list", environment.getProperty("kafka.metadata.broker.list"));
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		props.put("request.required.acks", environment.getProperty("kafka.request.required.acks"));
		props.put("partitioner.class", "com.hm.sample.SamplePartitioner");
		props.put("num.partitions", environment.getProperty("kafka.num.partitions"));
		props.put("log.retention.hours", environment.getProperty("kafka.log.retention.hours"));
		return props;
	}

	@Bean
	public ProducerConfig getProducerConfig() {
		return new ProducerConfig(getProperties());
	}

	@Bean
	public Producer<String, byte[]> getProducer() {
		return new Producer<>(this.getProducerConfig());
	}
	
	@Bean
	public String getSampleTopic() {
		return environment.getProperty("kafka.topic.sample.test");
	}

}