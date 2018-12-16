package com.apple.account.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.apple.account.data.entity.TotalInfo;
import com.apple.account.data.repository.ExpenseRepository;
import com.google.gson.Gson;

@RestController
public class RptController {
	private static Logger logger = LoggerFactory.getLogger(RptController.class);
	@Autowired
	public ExpenseRepository rep;

	@RequestMapping("rpt/getTotalByYear")
	public String getTotalByYear() {
		List<TotalInfo> list = new ArrayList<>();
		List _list = rep.getTotalByYear();
		String[] x = new String[_list.size()];
		float[] y = new float[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			Object[] cells = (Object[]) _list.get(i);
			x[i] = cells[0].toString();
			y[i] = Float.parseFloat(cells[1].toString());
		}
		Result r = new Result();
		r.setName(x);
		r.setData(y);
		Gson gs = new Gson();
		String s = gs.toJson(r);
		return s;
	}

	@RequestMapping("rpt/getTotalByItem")
	public String getTotalByItem() {
		List<TotalInfo> list = new ArrayList<>();
		List _list = rep.getTotalByItem();
		String[] x = new String[_list.size()];
		float[] y = new float[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			Object[] cells = (Object[]) _list.get(i);
			x[i] = cells[0].toString();
			y[i] = Float.parseFloat(cells[1].toString());
		}
		Result r = new Result();
		r.setName(x);
		r.setData(y);
		Gson gs = new Gson();
		String s = gs.toJson(r);
		return s;
	}

	
	@RequestMapping("rpt/getTotalThisYearByMonth")
	public String getTotalThisYearByMonth() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<TotalInfo> list = new ArrayList<>();
		List _list = rep.getTotalThisYearByMonth(year);
		String[] x = new String[_list.size()];
		float[] y = new float[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			Object[] cells = (Object[]) _list.get(i);
			x[i] = cells[0].toString();
			y[i] = Float.parseFloat(cells[1].toString());
		}
		Result r = new Result();
		r.setName(x);
		r.setData(y);
		Gson gs = new Gson();
		String s = gs.toJson(r);
		return s;
	}

	@RequestMapping("rpt/getAllTotalThisYearByItem")
	public String getAllTotalThisYearByItem() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<TotalInfo> list = new ArrayList<>();
		List _list = rep.getAllTotalThisYearByItem(year);
		String[] x = new String[_list.size()];
		float[] y = new float[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			Object[] cells = (Object[]) _list.get(i);
			x[i] = cells[0].toString();
			y[i] = Float.parseFloat(cells[1].toString());
		}
		Result r = new Result();
		r.setName(x);
		r.setData(y);
		Gson gs = new Gson();
		String s = gs.toJson(r);
		return s;
	}

}

class Result {
	private String[] name;
	private float[] data;

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public float[] getData() {
		return data;
	}

	public void setData(float[] data) {
		this.data = data;
	}
}
