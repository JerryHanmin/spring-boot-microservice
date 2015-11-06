package com.hm.sample;

import com.hm.sample.entity.Company;
import com.hm.sample.repository.CompanyRepository;
import com.hm.sample.utils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
public class CompanyCassandraApp {

    private final Logger logger = LoggerFactory.getLogger(CompanyCassandraApp.class);

    @Autowired
    CompanyRepository companyRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CompanyCassandraApp.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "sample company with cassandra";
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> getCompanies() {
        return new ResponseEntity<Resource>(new Resource<>(companyRepository.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> addCompany(@RequestBody Company company) {
        try {
            UUID uuid = UUID.randomUUID();
            company.setUuid(uuid);
            companyRepository.save(company);

            HttpHeaders headers = new HttpHeaders();
            URI uri = linkTo(methodOn(this.getClass()).getCompanyByID(uuid)).toUri();
            headers.setLocation(uri);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/companies/{uuid}", method = RequestMethod.PATCH, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> editCompany(@PathVariable UUID uuid, @RequestBody Company company) {
        try {
            Company existcompany = companyRepository.findOne(new BasicMapId().with("uuid", uuid));
            if (null == existcompany)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                BeanUtils.copyPropertiesIgnoreNullValue(company, existcompany);
                companyRepository.save(existcompany);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/companies/{uuid}", method = RequestMethod.GET, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> getCompanyByID(@PathVariable UUID uuid) {
        Company company = companyRepository.findOne(new BasicMapId().with("uuid", uuid));
        if (null == company) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Resource>(new Resource<>(company), HttpStatus.OK);
        }
    }
}
