package com.hm.sample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppKafkaListener implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private KafkaConsumerConfiguration kafkaConsumerConfiguration;

	private KafkaConsumerGroup kafkaConsumerGroup = null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(null == kafkaConsumerGroup){
			kafkaConsumerGroup = KafkaConsumerGroup.getInstance(kafkaConsumerConfiguration);
			kafkaConsumerGroup.run();
		}
	}

}
