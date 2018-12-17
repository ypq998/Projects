package com.apple.account.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
@EnableHystrixDashboard
@EnableTurbine
public class Hystrix {

	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Hystrix.class, args);
	}



}
