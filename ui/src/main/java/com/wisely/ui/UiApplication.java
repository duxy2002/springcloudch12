package com.wisely.ui;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//通过向eureka服务发现注册的可用的eureka-server，向后端发送请求
@EnableEurekaClient
//注解EnableFeignClients表明需要扫描使用FeignClient注解的接口
@EnableFeignClients
//@EnableCircuitBreaker注解开启断路器功能
//使用EnableCircuitBreaker或者 EnableHystrix 表明Spring boot工程启用hystrix,两个注解是等价的.
@EnableCircuitBreaker
//其中EnableHystrixDashboard注解表示启动对hystrix的监控
@EnableHystrixDashboard
public class UiApplication {
    @Bean
    @LoadBalanced
    //RestTemplate 构建RestTemplate对应的bean，在method上使用注解LoadBalanced表示restTemplate使用LoadBalancerClient执行请求
    RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;


    }
	public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
