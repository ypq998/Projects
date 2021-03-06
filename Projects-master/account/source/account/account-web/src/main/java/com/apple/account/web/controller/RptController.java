package com.apple.account.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RptController {
	private static Logger logger = LoggerFactory.getLogger(RptController.class);

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings("unused")
	private String getBrokenDown() {
		return "brokenDown";
	}

	@Value("${serviceUrl}")  
	private String serviceUrl; 

	// @HystrixCommand(fallbackMethod = "getBrokenDown")
	@RequestMapping("rpt/getTotalByYear")
	public String getTotalByYear() {
		String rel = restTemplate.getForEntity(serviceUrl+"/rpt/getTotalByYear", String.class).getBody();
		return rel;
	}

	@RequestMapping("rpt/getTotalByItem")
	public String getTotalByItem() {
		return restTemplate.getForEntity(serviceUrl+"/rpt/getTotalByItem", String.class).getBody();
	}

	@RequestMapping("rpt/getTotalThisYearByMonth")
	public String getTotalThisYearByMonth() {
		return restTemplate.getForEntity(serviceUrl+"/rpt/getTotalThisYearByMonth", String.class).getBody();
	}

	@RequestMapping("rpt/getAllTotalThisYearByItem")
	public String getAllTotalThisYearByItem() {
		return restTemplate.getForEntity(serviceUrl+"/rpt/getAllTotalThisYearByItem", String.class).getBody();
	}

}
