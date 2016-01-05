package com.hm.sample.config;

import com.hm.sample.repository.SampleKafkaRepositoy;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfiguration {
	@Autowired
	private Environment environment;
	@Autowired
	private SampleKafkaRepositoy sampleKafkaRepositoy;
	@Autowired
	RestTemplate restTemplate;

	@Bean
	public ConsumerConfig kafkaConnectionFactory() {
		Properties props = new Properties();
		// zookeeper
		props.put("zookeeper.connect", environment.getProperty("kafka.zookeeper.connect"));
		// group
		props.put("group.id", environment.getProperty("kafka.group.id"));
		// zk
		props.put("zookeeper.session.timeout.ms", environment.getProperty("kafka.zookeeper.session.timeout.ms"));
		props.put("zookeeper.sync.time.ms", environment.getProperty("kafka.zookeeper.sync.time.ms"));
		props.put("auto.commit.interval.ms", environment.getProperty("kafka.auto.commit.interval.ms"));
		props.put("auto.offset.reset", environment.getProperty("kafka.auto.offset.reset"));
		props.put("serializer.class", "kafka.serializer.DefaultEncoder");
		return new ConsumerConfig(props);
	}

	@Bean
	public ConsumerConnector getConsumerConnector() {
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(kafkaConnectionFactory());
		return consumer;
	}

	@Bean
	public String getSampleTopic() {
		return environment.getProperty("kafka.topic.sample.test");
	}

	@Bean
	public int getNumThreads() {
		String numThreads = environment.getProperty("kafka.consumer.thread.num");
		if (null != numThreads && !"".equals(numThreads)) {
			return Integer.parseInt(numThreads);
		}
		return 1;
	}

	@Bean
	public SampleKafkaRepositoy getSampleKafkaRepositoy(){
		return sampleKafkaRepositoy;
	}
	@Bean
	public RestTemplate getRestTemplate(){
		return restTemplate;
	}
}