package com.hm.sample.repository;

import com.hm.sample.entity.SampleKafka;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface SampleKafkaRepositoy extends ElasticsearchRepository<SampleKafka,String> {

}
