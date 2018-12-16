package com.apple.account.data.entity;

import javax.persistence.*;


@Entity
@Table(name = "EXPENSETYPE1")
public class ExpenseType1 implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
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
