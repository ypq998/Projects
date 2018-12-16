//package com.apple.account.web.controller;
//
//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.apple.account.data.entity.Expense;
//
//
//
//@FeignClient(name= "provider")
//public interface Remote {
//	 @RequestMapping(value = "/actor/getLatestExpByItemID")
//	    public String getLatestExpByItemID(@RequestParam(value = "itemID") Long itemID);
//
//		@RequestMapping(method = RequestMethod.POST,  value = "/actor/getExpByID")
//		public Expense getExpByID(@RequestParam(value = "id")Long id);
//}
