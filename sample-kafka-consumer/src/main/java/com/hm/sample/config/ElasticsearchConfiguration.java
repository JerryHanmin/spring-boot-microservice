package com.hm.sample.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.hm.sample.repository")
public class ElasticsearchConfiguration {
	
	@Resource
	private Environment environment;
	
	@Bean
	public Client client() {
		String ipAddress = environment.getRequiredProperty("elasticsearch.host");
		String portAddress = environment.getRequiredProperty("elasticsearch.port");
		String clusterName = environment.getRequiredProperty("elasticsearch.clusterName");
		String sniff = environment.getRequiredProperty("elasticsearch.sniff");
		String ignore_cluster_name = environment.getRequiredProperty("elasticsearch.ignore_cluster_name");
		String ping_timeout = environment.getRequiredProperty("elasticsearch.ping_timeout");
        String nodes_sampler_interval = environment.getRequiredProperty("elasticsearch.nodes_sampler_interval");
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", clusterName)
				.put("client.transport.sniff", Boolean.parseBoolean(sniff))
				.put("client.transport.ignore_cluster_name", Boolean.parseBoolean(ignore_cluster_name))
				.put("client.transport.ping_timeout", ping_timeout)
				.put("client.transport.nodes_sampler_interval", nodes_sampler_interval)
				.build();
		TransportClient client = new TransportClient(settings,false)
        .addTransportAddress(new InetSocketTransportAddress(ipAddress,Integer.valueOf(portAddress)));
		return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(client());
	}
}