package com.wisely.some;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现，才能实现Controller中对服务信息的输出。
@EnableDiscoveryClient
@RestController
public class SomeApplication {
	 @Value("${my.message}") //1
	 private String message; 
	
	 @RequestMapping(value = "/getsome")
	 public String getsome(){
		 return message;
	 }
	public static void main(String[] args) {
		SpringApplication.run(SomeApplication.class, args);
	}

}
