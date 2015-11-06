package com.hm.sample.repository;


import com.hm.sample.entity.Company;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CompanyRepository extends ElasticsearchRepository<Company, String> {

}
