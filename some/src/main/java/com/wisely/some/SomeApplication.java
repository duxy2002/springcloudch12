package com.wisely.some;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//主类中通过加上@EnableDiscoveryClient注解，该注解能激活Eureka中的DiscoveryClient实现，才能实现Controller中对服务信息的输出。
@EnableDiscoveryClient
@RestController
//post方式执行http://localhost/refresh 会刷新env中的配置
@RefreshScope
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SomeApplication {
	@Value("${my.message}") //1
	private String message;

	private final Logger logger = Logger.getLogger(getClass());

    //通过 DiscoveryClient 对象，在日志中打印出服务实例的相关内容。
	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/getsome")
	public String getsome(){
		ServiceInstance instance = client.getLocalServiceInstance();
		logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
		return message;
	}
	public static void main(String[] args) {
		SpringApplication.run(SomeApplication.class, args);
	}

}
