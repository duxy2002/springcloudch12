package com.wisely.person;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class PersonApplication {
	
	   public static void main(String[] args) {
	        SpringApplication.run(PersonApplication.class, args);
	    }

}
