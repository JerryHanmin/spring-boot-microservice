package com.hm.sample;

import com.hm.sample.entity.Company;
import com.hm.sample.repository.CompanyRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Configuration
@EnableAutoConfiguration
@RestController
@EnableDiscoveryClient
public class CompanyEsApp {

    private final Logger logger = LoggerFactory.getLogger(CompanyEsApp.class);

    @Autowired
    CompanyRepository companyRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CompanyEsApp.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "sample company with cassandra";
    }

    @RequestMapping(value = "/companies", method = RequestMethod.GET, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> getCompanies(@PageableDefault(page = 0, size = 20, sort = "name", direction = Sort.Direction.DESC) Pageable pageable,String name) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        if (StringUtils.isNotEmpty(name))
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("name", name));
        else
            nativeSearchQueryBuilder.withQuery(matchAllQuery());

        nativeSearchQueryBuilder.withPageable(pageable);
        Page<Company> page = companyRepository.search(nativeSearchQueryBuilder.build());

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> addCompany(@RequestBody Company company) {
        try {
            UUID uuid = UUID.randomUUID();
            company.setUuid(uuid.toString());
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
            Company existcompany = companyRepository.findOne(uuid.toString());
            if (null == existcompany)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {
                companyRepository.save(company);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/companies/{uuid}", method = RequestMethod.GET, produces = "application/hal+json;charset=UTF-8")
    public HttpEntity<?> getCompanyByID(@PathVariable UUID uuid) {
        Company company = companyRepository.findOne(uuid.toString());
        if (null == company) {
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Resource>(new Resource<>(company), HttpStatus.OK);
        }
    }
}
