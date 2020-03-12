package com.example.batch;

import org.springframework.boot.CommandLineRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldCommandLineRunner implements CommandLineRunner {
	@Override
	public void run(String... strings) throws Exception {
		log.info("Hello, ...");
		Thread.sleep(5000);
		log.info("... World");
	}

}
