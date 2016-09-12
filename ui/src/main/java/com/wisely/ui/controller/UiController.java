package com.wisely.ui.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.ui.domain.Person;
import com.wisely.ui.service.PersonHystrixService;
import com.wisely.ui.service.SomeHystrixService;

import java.util.List;

@RestController
public class UiController {
	@Autowired
	private SomeHystrixService someHystrixService;
	
	@Autowired
	private PersonHystrixService personHystrixService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UiController.class);

	@RequestMapping("/dispatch")
	public List<Person> sendMessage(@RequestBody String personName) {
		LOGGER.info("peron call: {}" , personName);
		return personHystrixService.save(personName);
	}
	
	@RequestMapping(value = "/getsome",produces={MediaType.TEXT_PLAIN_VALUE})
	public String getSome(){
		LOGGER.info("getsome call: {}");
		return someHystrixService.getSome();
	}
}
