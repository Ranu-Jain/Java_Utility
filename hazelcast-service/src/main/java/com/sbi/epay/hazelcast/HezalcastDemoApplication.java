package com.sbi.epay.hazelcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HezalcastDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HezalcastDemoApplication.class, args);
	}

}
