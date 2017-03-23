package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
	// created by TraceWebAsyncClientAutoConfiguration
	private final AsyncRestTemplate traceAsyncRestTemplate;
		
	@Value("${server.port}")
	private String port;
	
	@Value("${app.activatesSleep:true}")
	private boolean activatesSleep;
	
	@RequestMapping(value = "/bean")
	public HogeBean bean() {
		log.info("(/bean) I got a request!");
		
		return HogeBean.builder()
				.name("test")
				.age(18)
				.build();
	}
	
	@RequestMapping(value = "/async-test")
	public void asyncTest() throws InterruptedException {
		log.info("(/async-test) I got a request!");
		
		ListenableFuture<ResponseEntity<HogeBean>> res = traceAsyncRestTemplate.getForEntity("http://localhost:" + port + "/bean", HogeBean.class);
		
		if (activatesSleep) {
			Thread.sleep(1000);
		}
		
		res.addCallback(
				success -> {
					log.info("(/async-test) success");
					},
				failure -> {
					log.error("(/async-test) failure", failure);
					}
				);
	}
}
