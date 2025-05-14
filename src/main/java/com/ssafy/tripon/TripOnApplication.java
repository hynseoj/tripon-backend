package com.ssafy.tripon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TripOnApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripOnApplication.class, args);
	}

}
