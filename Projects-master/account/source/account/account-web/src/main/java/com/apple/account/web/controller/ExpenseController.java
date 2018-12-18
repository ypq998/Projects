package com.apple.account.web.controller;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.apple.account.data.entity.Expense;
import com.apple.account.data.entity.Item;
import com.apple.account.data.entity.MyPage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

@Controller
public class ExpenseController {
	private static Logger logger = LoggerFactory.getLogger(ExpenseController.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${serviceUrl}")  
	private String serviceUrl;  
	
	@RequestMapping("/")
	public String index() {
		logger.info("index");
		return "index";
	}

	@RequestMapping("actor/index")
	public String welcome(Model model) {
		return "actor/list";
	}

	// ****************LIST
	@RequestMapping("actor/list")
	@ResponseBody
	public MyPage<Expense> getList(HttpServletRequest request) {
		String name = request.getParameter("name");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		if (page == null)
			page = "0";
		if (size == null)
			size = "20";
		String rel = "";
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.create();
		if (name != null && name.trim().length() > 0) {
			// PageRequest pageable =new PageRequest(Integer.parseInt(page),
			// Integer.parseInt(size),
			// null);
			// rel = rep.findByNameLike(name, pageable);
		} else {

			// PageRequest pageable =new PageRequest(Integer.parseInt(page),
			// Integer.parseInt(size),
			// new Sort(Sort.Direction.DESC, "createTime"));

			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("name", name);
			map.add("page", page);
			map.add("size", size);
			rel = restTemplate.postForObject(serviceUrl + "/actor/list", map, String.class);

		}

		MyPage<Expense> list = gson.fromJson(rel, new TypeToken<MyPage<Expense>>() {

		}.getType());
		return list;
	}

	@RequestMapping("actor/torpt")
	public String toRpt() {
		return "actor/rpt";
	}

	// ***************SHOW
	@RequestMapping(value = "actor/show/{id}")
	public String show(ModelMap model, @PathVariable Long id) {
		MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
		map.add("id", id);
		String rel = restTemplate.postForObject(serviceUrl + "/actor/getExpByID", map, String.class);
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.create();
		Expense exp = gson.fromJson(rel, new TypeToken<Expense>() {
		}.getType());
		model.addAttribute("exp", exp);
		return "actor/show";
	}

	@RequestMapping(value = "actor/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@PathVariable Long id) {
		try {
			MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
			map.add("id", id);
			String rel = restTemplate.postForObject(serviceUrl + "/actor/deleteByID", map, String.class);
			return rel;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "0";

	}

	// *************ADD
	@RequestMapping("actor/new")
	public String toAdd(ModelMap model, Expense user) {// OK
		Gson gson = new Gson();
		String rel = restTemplate.getForEntity(serviceUrl + "/actor/getAllItem", String.class).getBody();
		List<Item> list = gson.fromJson(rel, new TypeToken<List<Item>>() {
		}.getType());
		model.addAttribute("items", list);
		return "actor/new";
	}

	@RequestMapping(value = "actor/save", method = RequestMethod.POST)
	@ResponseBody
	public String add(Expense exp, Long itemid) throws Exception {
		Item item = new Item();
		item.setId(itemid);
		exp.setItem(item);
		exp.setCreateTime(new Date());
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson = builder.create();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		String ss = gson.toJson(exp);
		map.add("exp", ss);
		String rel = restTemplate.postForObject(serviceUrl + "/actor/add", map, String.class);
		logger.info("新增->ID=" + exp.getId());
		return rel;
	}

	// @Autowired
	// Remote remote;

	@RequestMapping(value = "actor/getByItemID", method = RequestMethod.POST)
	@ResponseBody
	public Expense findByItemID(Long itemid) {
		if (itemid == null)
			return new Expense();
		MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
		map.add("itemID", itemid);
		String rel = restTemplate.postForObject(serviceUrl + "/actor/getLatestExpByItemID", map, String.class);
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.create();
		Expense exp = gson.fromJson(rel, new TypeToken<Expense>() {
		}.getType());
		return exp;
	}

	// *************EDIT
	@RequestMapping(value = "actor/edit/{id}")
	public String update(ModelMap model, @PathVariable Long id) {
		MultiValueMap<String, Long> map = new LinkedMultiValueMap<>();
		map.add("id", id);
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		});
		Gson gson = builder.create();
		String rel = restTemplate.postForObject(serviceUrl + "/actor/getExpByID", map, String.class);
		Expense exp = gson.fromJson(rel, new TypeToken<Expense>() {
		}.getType());
		model.addAttribute("exp", exp);

		rel = restTemplate.getForEntity(serviceUrl + "/actor/getAllItem", String.class).getBody();
		List<Item> list = gson.fromJson(rel, new TypeToken<List<Item>>() {
		}.getType());
		model.addAttribute("items", list);
		return "actor/edit";
	}

	@RequestMapping(method = RequestMethod.POST, value = "actor/update")
	@ResponseBody
	public String update(Expense exp, long itemid) throws Exception {
		Item item = new Item();
		item.setId(itemid);
		exp.setItem(item);
		MultiValueMap<String, Expense> map = new LinkedMultiValueMap<>();
		map.add("exp", exp);
		String rel = restTemplate.postForObject(serviceUrl + "/actor/update", map, String.class);

		logger.info("修改->ID=" + exp.getId());
		return rel;
	}

	// ************DELETE

	@RequestMapping("/movie/index")
	public String movielist(Model model) {
		return "movie/index";
	}

	@RequestMapping("/movie/list")
	public String elist(Model model) {
		return "movie/index";
	}
}
