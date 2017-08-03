package com.hm.sample.kafka.producer;

import com.hm.sample.kafka.KafkaTopicBean;
import com.hm.sample.kafka.KafkaZookeeperBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Created by Jerry on 2016/1/21 0021.
 */

@ConfigurationProperties("kafka.producer")
public class KafkaProducerBean {
    private KafkaProducerBrokerBean broker = new KafkaProducerBrokerBean();
    private KafkaZookeeperBean zookeeper = new KafkaZookeeperBean();
    private String serializer = "kafka.serializer.DefaultEncoder";
    private String acks = "0";
    private String partitioner = "com.changhongit.tcar.kafka.MessagePartitioner";
    private String partitions = "8";

    private List<KafkaTopicBean> topics;

    public KafkaZookeeperBean getZookeeper() {
        return zookeeper;
    }

    public void setZookeeper(KafkaZookeeperBean zookeeper) {
        this.zookeeper = zookeeper;
    }

    public List<KafkaTopicBean> getTopics() {
        return topics;
    }

    public void setTopics(List<KafkaTopicBean> topics) {
        this.topics = topics;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public String getPartitioner() {
        return partitioner;
    }

    public void setPartitioner(String partitioner) {
        this.partitioner = partitioner;
    }

    public String getPartitions() {
        return partitions;
    }

    public void setPartitions(String partitions) {
        this.partitions = partitions;
    }

    public KafkaProducerBrokerBean getBroker() {
        return broker;
    }

    public void setBroker(KafkaProducerBrokerBean broker) {
        this.broker = broker;
    }
}
