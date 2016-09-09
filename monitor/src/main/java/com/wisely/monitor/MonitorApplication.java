package com.wisely.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@SpringBootApplication
@EnableEurekaClient
// turbine是聚合服务器发送事件流数据的一个工具，hystrix的监控中，只能监控单个节点，实际生产中都为集群，因此可以通过
// turbine来监控集群下hystrix的metrics情况，通过eureka来发现hystrix服务。
@EnableHystrixDashboard
@EnableTurbine
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
