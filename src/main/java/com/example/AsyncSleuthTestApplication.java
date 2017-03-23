package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.client.TraceAsyncRestTemplate;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AsyncSleuthTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncSleuthTestApplication.class, args);
	}
	
    //@Bean
    TraceAsyncRestTemplate traceAsyncRestTemplate(RestTemplate restTemplate, Tracer tracer) {
    	SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
		factory.setTaskExecutor(new SimpleAsyncTaskExecutor());
		
        return new TraceAsyncRestTemplate((AsyncClientHttpRequestFactory)factory, restTemplate, tracer);
    }
    
    //@Bean
    RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
