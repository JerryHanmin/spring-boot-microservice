package com.hm.sample.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Jerry on 2015/11/6 0006.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "sample_kafka", type = "kafka_test", shards = 5, replicas = 2)
public class SampleKafka implements Serializable {
    @Id
    private String uuid;
    @Field(type = FieldType.String)
    private String name;
    @Field(type = FieldType.String)
    private String content;
    @Field(type = FieldType.Object)
    private Map<String, String> kafka_spend_time;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getKafka_spend_time() {
        return kafka_spend_time;
    }

    public void setKafka_spend_time(Map<String, String> kafka_spend_time) {
        this.kafka_spend_time = kafka_spend_time;
    }
}
