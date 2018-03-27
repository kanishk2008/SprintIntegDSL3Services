package com.hcsc.sprnl.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@ComponentScan (basePackages = {"com.hcsc.*"})
@EnableIntegration
@IntegrationComponentScan (basePackages = {"com.hcsc.*"})
public class SprnlServicesApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SprnlServicesApplication.class, args);
		
	}
}
