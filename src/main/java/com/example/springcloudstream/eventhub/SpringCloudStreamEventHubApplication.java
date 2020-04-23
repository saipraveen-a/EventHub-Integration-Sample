package com.example.springcloudstream.eventhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCloudStreamEventHubApplication {
	public static final class Profiles {
		public static final String NO_DEPENDENCIES = "no-dependencies";

		public static final String PRODUCTION = "default";

	}
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamEventHubApplication.class, args);
	}
}
