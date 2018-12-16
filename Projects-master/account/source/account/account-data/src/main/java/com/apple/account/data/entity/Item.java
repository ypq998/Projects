package com.apple.account.data.entity;

import javax.persistence.*;


@Entity
@Table(name = "ITEM")
public class Item implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
	@OneToOne() // JPA注释： 一对一 关系
	@JoinColumn(name = "TYPE1_ID", referencedColumnName = "ID", nullable = true)
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
