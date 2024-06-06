package com.example.ISA2023.back;

import com.example.ISA2023.back.loadBalancer.LoadBalancerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableCaching
public class Isa2023BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(Isa2023BackApplication.class, args);
	}
}
