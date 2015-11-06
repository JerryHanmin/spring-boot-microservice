package com.hm.sample.repository;

import com.hm.sample.entity.Company;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CompanyRepository extends CassandraRepository<Company> {
}
