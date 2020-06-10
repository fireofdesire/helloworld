package com.mybatis.pojo;

import java.io.Serializable;

public class Employee 
{
    private Integer id;
    private String name;
    private Integer gender;
    private String email;
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", email=" + email + "]";
	}
	public Employee() {
		super();
	}
	public Employee(Integer id, String name, Integer gender, String email) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
