package com.apple.account.data.entity;

import javax.persistence.*;



public class Item implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

	private ExpenseType1 type1;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    


    

 
}
