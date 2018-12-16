package com.apple.account.controller;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apple.account.data.entity.Expense;
import com.apple.account.data.entity.Item;
import com.apple.account.data.repository.ExpenseRepository;
import com.apple.account.data.repository.ExpenseType1Repository;
import com.apple.account.data.repository.ItemRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

@RestController
public class ExpenseController {
	private static Logger logger = LoggerFactory.getLogger(ExpenseController.class);
	@Autowired
	public ExpenseRepository rep;
	@Autowired
	public ItemRepository itemrep;
	@Autowired
	public ExpenseType1Repository typerep;

	// ****************LIST
	@RequestMapping(value = "actor/list", method = RequestMethod.POST)
	public Page<Expense> getList(String name, int page, int size) {
		Page<Expense> rel = null;
		if (name != null && name.trim().length() > 0) {
//			PageRequest pageable =new PageRequest(Integer.parseInt(page), Integer.parseInt(size),
//					null);
//			rel = rep.findByNameLike(name, pageable);
		} else {
			PageRequest pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createTime"));
			rel = rep.findAll(pageable);
		}
		return rel;
	}

	@RequestMapping(value = "actor/getLatestExpByItemID", method = RequestMethod.POST)
	public Expense getLatestExpByItemID(long itemID) {
		Expense exp = rep.findByItemID(itemID);
		return exp;
	}

	/**
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "actor/add", method = RequestMethod.POST)
	public String add(String exp) throws Exception {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		try {
			Expense exp1 = gson.fromJson(exp, Expense.class);
			rep.save(exp1);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return "1";
	}

	// ***************SHOW
	@RequestMapping(method = RequestMethod.POST, value = "actor/getExpByID")
	public Expense getExpByID(long id) {
		Expense ee = rep.findOne(id);
		return ee;
	}
	@RequestMapping(value = "actor/deleteByID", method = RequestMethod.POST)
	public String delete(long id) {
		rep.delete(id);
		return "1";
	}
	// *************ADD
	@RequestMapping("actor/getAllItem")
	public List<Item> getAllItem() {
		List<Item> listItem = itemrep.findAll();
		return listItem;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actor/update")
	public String update(String exp) throws Exception {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();
		try {
			Expense exp1 = gson.fromJson(exp, Expense.class);
			rep.save(exp1);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return "1";
	}

	// ************DELETE


}
