package com.hm.sample.config;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfiguration {
	@Autowired
	private Environment environment;
	@Bean
	public ConsumerConfig kafkaConnectionFactory() {
		Properties props = new Properties();
		// zookeeper
		props.put("zookeeper.connect", environment.getProperty("zookeeper.connect"));
		// group
		props.put("group.id", environment.getProperty("group.id"));
		// zk
		props.put("zookeeper.session.timeout.ms", environment.getProperty("zookeeper.session.timeout.ms"));
		props.put("zookeeper.sync.time.ms", environment.getProperty("zookeeper.sync.time.ms"));
		props.put("auto.commit.interval.ms", environment.getProperty("auto.commit.interval.ms"));
		props.put("auto.offset.reset", environment.getProperty("auto.offset.reset"));
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		return new ConsumerConfig(props);
	}

	@Bean
	public ConsumerConnector getConsumerConnector() {
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(kafkaConnectionFactory());
		return consumer;
	}

	@Bean
	public String getSampleTopic() {
		return environment.getProperty("kafka.sample.topic");
	}

	@Bean
	public int getNumThreads() {
		String numThreads = environment.getProperty("kafka.consumer.thread.num");
		if (null != numThreads && !"".equals(numThreads)) {
			return Integer.parseInt(numThreads);
		}
		return 1;
	}
}