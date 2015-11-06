package com.hm.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppKafkaListener implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private KafkaConsumerConfiguration kafkaConsumerConfiguration;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new KafkaConsumerGroup(kafkaConsumerConfiguration).run();
	}

}
