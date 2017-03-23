package com.example;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Task {
	@Async
	void doSomething() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		
		log.info("doSomething() done");
	}
}
