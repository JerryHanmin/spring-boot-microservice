package com.hm.sample.config;

import com.hm.sample.model.Message;
import com.hm.sample.utils.JsonUtils;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleConsumer implements Runnable{
	private final Logger logger = LoggerFactory.getLogger(getClass());
    private KafkaStream<byte[], byte[]> m_stream;
    private int m_threadNumber;

	public SampleConsumer(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber) {
		this.m_threadNumber = a_threadNumber;
        this.m_stream = a_stream;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        String jsonStr;
		Message message;
		while (it.hasNext()) {
			jsonStr = new String(it.next().message());
			if(StringUtils.isNotEmpty(jsonStr)){
				try {
					message = JsonUtils.fromJson(jsonStr, Message.class);
					logger.debug("message name: " + message.getName());
					logger.debug("message content: " + message.getContent());
					logger.debug("------------------------------------------------");
				} catch (Exception e) {
					logger.error("JsonSyntaxException:",e);
				}
			}
		}
	}
}
