package com.hm.sample.config;

import com.google.common.collect.Lists;
import com.hm.sample.entity.SampleKafka;
import com.hm.sample.model.Message;
import com.hm.sample.model.MessageRequest;
import com.hm.sample.repository.SampleKafkaRepositoy;
import com.hm.sample.utils.JsonUtils;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SampleConsumer implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private KafkaStream<byte[], byte[]> m_stream;
    private SampleKafkaRepositoy sampleKafkaRepositoy;
    private RestTemplate restTemplate;

    public SampleConsumer(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber, KafkaConsumerConfiguration kafkaConsumerConfiguration) {
        this.m_stream = a_stream;
        this.sampleKafkaRepositoy = kafkaConsumerConfiguration.getSampleKafkaRepositoy();
        this.restTemplate = kafkaConsumerConfiguration.getRestTemplate();
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        String jsonStr;
        Message message;
        while (it.hasNext()) {
            jsonStr = new String(it.next().message());
            if (StringUtils.isNotEmpty(jsonStr)) {
                try {
                    long active_time = System.currentTimeMillis();
                    message = JsonUtils.fromJson(jsonStr, Message.class);
                    long kafka_spend_time = active_time - message.getCreated_time();

                    SampleKafka sampleKafka = new SampleKafka();
                    sampleKafka.setName(message.getName());
                    sampleKafka.setContent(message.getContent());

                    Map<String, String> spend_time = new HashMap<>();
                    spend_time.put("kafka_spend_time", kafka_spend_time + "");
                    sampleKafka.setKafka_spend_time(spend_time);


                    MessageRequest messageRequest = new MessageRequest();

                    messageRequest.setType("kafka_test".toUpperCase());
                    messageRequest.setFrom(UUID.randomUUID().toString());
                    Map<String, String> detail = new HashMap<>();
                    detail.put("created_timestamp", message.getCreated_time() + "");

                    messageRequest.setDetail(detail);

                    long post_start_time = System.currentTimeMillis();
                    postEntityForMessageService(messageRequest);
                    long post_end_time = System.currentTimeMillis();

                    sampleKafka.getKafka_spend_time().put("post_message_spend_time", post_end_time - post_start_time + "");

                    sampleKafkaRepositoy.save(sampleKafka);
                    logger.debug("message name: " + message.getName());
                    logger.debug("message content: " + message.getContent());
                    logger.debug("------------------------------------------------");
                } catch (Exception e) {
                    logger.error("JsonSyntaxException:", e);
                }
            }
        }
    }


    private void postEntityForMessageService(MessageRequest messageRequest) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Content-Type", Lists.newArrayList("application/vnd.jiahua.commands.addMessage+json"));
        headers.put("X-Token", Lists.newArrayList("dda733c1-9a51-4d73-96c3-f57c877ed792"));
        restTemplate.exchange("http://loving-message-service/messageserver/messages", HttpMethod.POST, new HttpEntity<>(messageRequest, headers), ResponseEntity.class);
    }
}
