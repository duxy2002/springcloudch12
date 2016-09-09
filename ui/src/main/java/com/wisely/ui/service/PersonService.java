package com.wisely.ui.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wisely.ui.domain.Person;

// @FeignClient("person")
// FeignClient注解 该注解表示申明创建一个rest client bean,可以直接通过Autowired注入使用，如果ribbon在工程中启用，
// 则会使用load balance进行后端请求调用，可以为FeignClient指定value表明需要访问的serviceId
// 使用@FeignClient("api-gateway")指定要访问的service id
@FeignClient("api-gateway")
public interface PersonService {
	 @RequestMapping(method = RequestMethod.POST, value = "/save",
	            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody List<Person> save(@RequestBody String  name);
}
