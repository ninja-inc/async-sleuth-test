package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	private final Task task;
		
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
	
	@RequestMapping(value = "/trace-async-rest-template")
	public void asyncTest(@RequestParam(required = false) boolean isSleep) throws InterruptedException {
		log.info("(/trace-async-rest-template) I got a request!");
		
		ListenableFuture<ResponseEntity<HogeBean>> res = traceAsyncRestTemplate.getForEntity("http://localhost:" + port + "/bean", HogeBean.class);
		
		if (isSleep) {
			Thread.sleep(1000);
		}
		
		res.addCallback(
				success -> {
					log.info("(/trace-async-rest-template) success");
					},
				failure -> {
					log.error("(/trace-async-rest-template) failure", failure);
					}
				);
	}
	
	@RequestMapping(value = "/async-annotation")
	public void asyncTest2() throws InterruptedException {
		log.info("(/async-annotation) start");
		task.doSomething();
		log.info("(/async-annotation) end");
	}
}
