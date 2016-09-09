package com.wisely.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wisely.ui.domain.Person;

@Service
public class PersonHystrixService {

	@Autowired
	PersonService personService; 
	// HystrixCommand 表明该方法为hystrix包裹，可以对依赖服务进行隔离、降级、快速失败、快速重试等等hystrix相关功能
	// 该注解属性较多，下面讲解其中几个
	// •fallbackMethod 降级方法
    // •commandProperties 普通配置属性，可以配置HystrixCommand对应属性，例如采用线程池还是信号量隔离、熔断器熔断规则等等
    // •ignoreExceptions 忽略的异常，默认HystrixBadRequestException不计入失败
    // •groupKey() 组名称，默认使用类名称
    // •commandKey 命令名称，默认使用方法名
	@HystrixCommand(fallbackMethod = "fallbackSave") //1
	public List<Person> save(String name) {
		return personService.save(name);
	}
	
	public List<Person> fallbackSave(String name){ 
		List<Person> list = new ArrayList<Person>();
		Person p = new Person(name+"没有保存成功，Person Service 故障");
		list.add(p);
		return list;
	}
}
