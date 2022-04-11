package com.itstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItStoreApplication {

	public static final Logger LOG = LoggerFactory.getLogger(ItStoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ItStoreApplication.class, args);
	}

}
