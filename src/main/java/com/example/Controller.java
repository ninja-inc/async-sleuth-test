package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestOperations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
	// created by TraceWebAsyncClientAutoConfiguration
	private final AsyncRestOperations traceAsyncRestTemplate;
	
	@Value("${server.port}")
	private String port;
	
	@RequestMapping(value = "/bean")
	public HogeBean bean() {
		log.info("(/bean) I got a request!");
		return HogeBean.builder()
				.name("test")
				.age(18)
				.build();
	}
	
	@RequestMapping(value = "/async-test")
	public void asyncTest() {
		log.info("(/async-test) I got a request!");
		
		traceAsyncRestTemplate.getForEntity("http://localhost:" + port + "/bean", HogeBean.class)
			.addCallback(success -> {
				log.info("(/async-test) success");
			}, failure -> {
				log.error("(/async-test) failure", failure);
			});
	}
}
