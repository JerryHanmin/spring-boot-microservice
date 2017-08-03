package com.hm.sample.kafka.producer;

/**
 * Created by Jerry on 2016/1/25 0025.
 */
public class KafkaProducerBrokerBean {
    private String connect = "172.28.24.42:9092";

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }
}
